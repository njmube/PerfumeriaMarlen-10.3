/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.model.dao;

import java.util.List;

/**
 *
 * @author alfredo
 */
public interface EntityDAO<T> {
	void create(T entity) throws PreexistingEntityException;
	void update(T entity) throws NonexistentEntityException;
	void delete(T entity) throws NonexistentEntityException;
	List<T> listAll();	
}
