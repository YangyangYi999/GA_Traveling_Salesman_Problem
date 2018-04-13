/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.GA;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 * @author Yangyang Yi & Lu Bai
 */
public class MergeSort {
    public Comparable[] aC;
    public Comparable[] aux;
    //constructor
    public MergeSort(Comparable[] array) {
        aC = new Comparable[array.length];
        aux = new Comparable[array.length];
        for( int i=0; i<array.length; i++ ) {
            aC[i] = array[i];
        }
    }
    
    //sort
    public void sort( Comparable[] a, int lo, int hi ) {
        if( hi<= lo ) return;
        int mid = lo + (hi-lo)/2;
        sort(a, lo, mid);   //left
        sort(a, mid+1,hi);  //right
        merge(a,lo,mid,hi); //merge
    }
    
    //merge
    public void merge( Comparable[] a, int lo, int mid, int hi ) {
        int i = lo, j = mid+1;
        
        for( int k=lo; k<=hi; k++ )
            aux[k] = a[k];
        
        for( int k=lo; k<=hi; k++ ) {
            if( i>mid )     a[k] = aux[j++];
            else if( j>hi ) a[k] = aux[i++];
            else if( less(aux[j],aux[i]) )
                a[k] = aux[j++];
            else    a[k] = aux[i++];        
        }
    }
    
    private boolean less(Comparable x, Comparable y) {
        return ( (x.compareTo(y) < 0) );
    }
    
    private void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
    void show(Comparable[] a) {
        for(int i=0; i<a.length; i++) {
            System.out.println( "a["+i+"]: " + a[i] + " " );
        }
    }
    
    public boolean isSorted( Comparable[] a ) {
        for(int i=0; i<a.length-1; i++) {
            if( a[i].compareTo(a[i+1]) > 0 )
                return false;
        }
        return true;
    }

    
}



