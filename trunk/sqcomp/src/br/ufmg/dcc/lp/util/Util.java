/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufmg.dcc.lp.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Util implements Serializable{

    private Util() {
    }

    public static int searchBruteForce(Object[] array, Object value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

}
