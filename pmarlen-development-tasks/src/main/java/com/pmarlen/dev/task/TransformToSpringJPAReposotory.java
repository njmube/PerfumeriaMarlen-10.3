package com.pmarlen.dev.task;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * TransformToSpringJPAReposotory
 */
public class TransformToSpringJPAReposotory {

    public void transform(String fileName) throws IOException{
        InputStream    is = null;
        OutputStream   os = null;
        BufferedReader br = null;
        PrintStream    ps = null;


        is = new FileInputStream(fileName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024*8];
        int r = -1;
        while ( ( r = is.read(buffer, 0, buffer.length)) != -1) {
            baos.write(buffer, 0, r);
        }
        is.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        br = new BufferedReader(new InputStreamReader(bais));

        os = new FileOutputStream(fileName);
        ps = new PrintStream(os);
        
        String line = null;
        String lineTransformed = null;

        boolean alreadyTransformed = false;
        boolean importInserted= false;
        boolean repositoryAnotationInserted= false;
        boolean metodsInserted= false;
        String className = null;
        String spriingBeanName = null;
        while ( ( line = br.readLine() ) != null) {

            lineTransformed =  line;

            if(line.startsWith("import org.springframework.stereotype.Repository;") && !importInserted){
                alreadyTransformed = true;
                System.err.println("Already trasnformed !");
            }
            if(! alreadyTransformed){
                if(line.startsWith("import ") && !importInserted){

                    ps.println("import org.springframework.stereotype.Repository;");
                    ps.println("import org.springframework.beans.factory.annotation.Autowired;");
                    ps.println("");

                    importInserted = true;
                } else if(line.startsWith("public class ") && !repositoryAnotationInserted){

                    String[] lineTokens = line.split(" ");
                    className = lineTokens[2].trim();
                    spriingBeanName = className.toLowerCase().substring(0,1)+className.substring(1);

                    ps.println("");
                    ps.println("@Repository(\""+spriingBeanName+"\")");
                    ps.println("");

                    repositoryAnotationInserted = true;
                } else if(repositoryAnotationInserted && line.contains("public "+className+"()") && !metodsInserted){

                    ps.println("");
                    ps.println("    private EntityManagerFactory emf = null;");
                    ps.println("");
                    ps.println("    @Autowired");
                    ps.println("    public void setEntityManagerFactory(EntityManagerFactory emf) {");
                    ps.println("        this.emf = emf;");
                    ps.println("    }");
                    ps.println("");

                    lineTransformed = br.readLine();
                    lineTransformed = br.readLine();
                    lineTransformed = br.readLine();
                    lineTransformed = br.readLine();

                    metodsInserted = true;
                }
            }

            ps.println(lineTransformed);
        }

    }


    public static void main(String[] args) {
        TransformToSpringJPAReposotory t2sjpar = new TransformToSpringJPAReposotory();

        if(args.length != 1) {
            System.err.println("usage:  TransformToSpringJPAReposotory  pathToFileToTransform");
            System.exit(1);
        }

        try {
            
            t2sjpar.transform(args[0]);
            
        } catch(IOException ioe) {
            ioe.printStackTrace(System.err);
        }

    }
}
