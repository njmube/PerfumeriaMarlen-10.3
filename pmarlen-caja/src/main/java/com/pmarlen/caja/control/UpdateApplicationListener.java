/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.caja.control;

/**
 *
 * @author alfredo
 */
interface UpdateApplicationListener {
	void updateProgress(int percentAdvance);
	void finisUpdate();
	void cancelUpdate();
	void errorUpdate(String error);
}
