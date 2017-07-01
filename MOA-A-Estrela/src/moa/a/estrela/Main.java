/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moa.a.estrela;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Mayza Hirose
 */
public class Main {

    static Set<Node> estadosAbertos = new TreeSet<>();
    static Set<Node> estadosFechados = new TreeSet<>();
    static int[][] listaInicial = {{2,1,5,9}, {3,6,10,13}, {4,7,11,14}, {0,8,12,15}};
    static int[][] listaFinal = {{1,5,9,13}, {2,6,10,14}, {3,7,11,15}, {4,8,12,0}};
    static Node menorEstado;

    public static void main(String[] args) {
        aEstrela();
    }

    static void aEstrela() {
        Node estadoInicial = new Node();
        estadoInicial.setEstado(listaInicial);
        estadoInicial.setHeuristica(calculaHeuristica1(estadoInicial));
        estadoInicial.setCustoG(0);
        estadoInicial.setCustoF(estadoInicial.getHeuristica() + estadoInicial.getCustoG());
        estadoInicial.setPai(null);
        estadoInicial.setFilhos(filhosNode(estadoInicial));
        estadosAbertos.add(estadoInicial);
        menorEstado = estadosAbertos.iterator().next();
        estadosAbertos.remove(menorEstado);
        estadosFechados.add(menorEstado);
        while ((estadosAbertos.size() != 0) && !isEstadoFinal(menorEstado)) {                    
        }
        /*insere o node inicial na lista
        para cada estado filho possivel, poe no aberto com cada um tendo o node pai
        remove o node pai dos abertos e poe nos fechados
        calcula melhor nodo a ser percorrido (calcula as heuristicas)*/
    }

    static int calculaHeuristica1(Node node) {
        int heuristica = 0;
        for (int i=0; i<4; i++) {
            for(int j=0; j<4; j++){
                if ((node.getEstado()[i][j] != listaFinal[i][j]) && (node.getEstado()[i][j] != 0)) {
                    heuristica++;                   
                }
            }
        }
        return heuristica;
    }
    
    static boolean isEstadoFinal(Node node){
        return Arrays.deepEquals(node.getEstado(), listaFinal);
    }

    static Set filhosNode(Node node) throws CloneNotSupportedException{
        Set<Node> filhos = new TreeSet();
        for (int row=0; row<4; row++) {
            for(int col=0; col<4; col++){
                if (node.getEstado()[row][col] == 0){
                    boolean isBordaEsq = isBordaEsq(col);
                    boolean isBordaDir = isBordaDir(col);
                    boolean isBordaCima = isBordaCima(row);
                    boolean isBordaBaixo = isBordaBaixo(row);
                    if (isBordaEsq) {
                        Node dir = node.clone();
                        dir.getEstado()[row][col] = node.getEstado()[row][col+1];
                        if (isBordaCima) {
                            Node baixo = node.clone();
                            baixo.
                            return filhos;
                        } else if (isBordaBaixo) {
                            Node cima = node.clone();
                            return filhos;
                        } else {
                            Node cima = node.clone();
                            Node baixo = node.clone();
                            return filhos;
                        }
                    } else {
                        Node esq = node.clone();
                        if (isBordaCima) {
                            Node baixo = node.clone();
                            return filhos;
                        } else if (isBordaBaixo) {
                            Node cima = node.clone();
                            return filhos;
                        } else {
                            Node cima = node.clone();
                            Node baixo = node.clone();
                            return filhos;
                        }
                    }
                }
            }
        }
        return filhos;
    }
    
    static boolean isBordaDir(int col) {
        return (((col + 1) % 4) == 0);
    }

    static boolean isBordaEsq(int col) {
        return ((col % 4) == 0);
    }

    static boolean isBordaCima(int row) {
        return (row == 0);
    }

    static boolean isBordaBaixo(int row) {
        return (row == 3);
    }    
    /*static Set filhosNode(Node estado) {
        Set<Node> filhos = new TreeSet();
        int posZero = posZero(estado);
        boolean isBordaEsq = isBordaEsq(posZero);
        boolean isBordaDir = isBordaDir(posZero);
        boolean isBordaCima = isBordaCima(posZero);
        boolean isBordaBaixo = isBordaBaixo(posZero);
        if (isBordaEsq) {
            Node dir;
            if (isBordaCima) {
                Node baixo;
                return filhos;
            } else if (isBordaBaixo) {
                Node cima;
                return filhos;
            } else {
                Node cima;
                Node baixo;
                return filhos;
            }
        } else {
            Node esq;
            if (isBordaCima) {
                Node baixo;
                return filhos;
            } else if (isBordaBaixo) {
                Node cima;
                return filhos;
            } else {
                Node cima;
                Node baixo;
                return filhos;
            }
        }
    }

    static int posZero(Node node) {
        int pos = 0;
        for (int i=0; i<4; i++) {
            for(int j=0; j<4; j++){
                if (node.getEstado()[i][j] == 0) {
                    return pos;
                }
                pos++;
            }
        }
        return pos;
    }*/

    /*static boolean isBordaDir(int posZero) {
        return (((posZero + 1) % 4) == 0);
    }

    static boolean isBordaEsq(int posZero) {
        return (((posZero) % 4) == 0);
    }

    static boolean isBordaCima(int posZero) {
        return (posZero < 4);
    }

    static boolean isBordaBaixo(int posZero) {
        return ((posZero + 4) >= 16);
    }*/
 
}

class Node implements Comparable<Node>, Cloneable {

    private Node pai;
    private Set<Node> filhos; 
    private int heuristica;
    private int custoG;
    private int custoF;  
    private int[][] estado;

    //<editor-fold defaultstate="collapsed" desc=" Getters and Setters ">
    public int getHeuristica() {
        return heuristica;
    }

    public void setHeuristica(int heuristica) {
        this.heuristica = heuristica;
    }

    public int getCustoG() {
        return custoG;
    }

    public void setCustoG(int custoG) {
        this.custoG = custoG;
    }
    
    public int getCustoF() {
        return custoF;
    }

    public void setCustoF(int custoF) {
        this.custoF = custoF;
    }

    public int[][] getEstado() {
        return estado;
    }

    public void setEstado(int[][] estado) {
        this.estado = estado;
    }

    public Node getPai() {
        return pai;
    }

    public void setPai(Node pai) {
        this.pai = pai;
    }

    public Set<Node> getFilhos() {
        return filhos;
    }

    public void setFilhos(Set<Node> filhos) {
        this.filhos = filhos;
    }
    //</editor-fold>
      
    @Override
    public int compareTo(Node o) {
        return custoF - o.custoF;
    }

    //compara pelo tabuleiro de estado
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
    
    @Override
    public Node clone() throws CloneNotSupportedException {
        return (Node) super.clone();
    }
}