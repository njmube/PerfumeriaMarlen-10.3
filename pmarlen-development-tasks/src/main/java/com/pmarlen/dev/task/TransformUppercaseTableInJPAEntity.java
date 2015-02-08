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
public class TransformUppercaseTableInJPAEntity {

    public void trasnform(String fileName) throws IOException{
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

        String tableName = null;
        String joinTableName = null;
        while ( ( line = br.readLine() ) != null) {

            lineTransformed =  line;

            if(line.startsWith("@Table(name = \"")){
                tableName = line.substring(line.indexOf("\"")+1,line.lastIndexOf("\"")).toUpperCase();
                lineTransformed = "@Table(name = \"" +tableName+"\")";

            } else if(line.contains("@JoinTable(name = \"")){

                joinTableName = line.substring(line.indexOf("\"")+1,line.lastIndexOf("\"")).toUpperCase();
                lineTransformed = line.substring(0,line.indexOf("\"")+1) + joinTableName + line.substring(line.lastIndexOf("\""));
            }

            ps.println(lineTransformed);
        }

    }


    public static void main(String[] args) {
        TransformUppercaseTableInJPAEntity t2sjpar = new TransformUppercaseTableInJPAEntity();

        if(args.length != 1) {
            System.err.println("usage:  TransformUppercaseTableInJPAEntity  pathToFileToTransform");
            System.exit(1);
        }

        try {
            
            t2sjpar.trasnform(args[0]);
            
        } catch(IOException ioe) {
            ioe.printStackTrace(System.err);
        }

    }
}
