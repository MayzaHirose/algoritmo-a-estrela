/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moa.a.estrela;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author MayzaHirose
 */
public class Main3 {
    static Set<Node> treeAbertos = new TreeSet<>();
    static Map<String, Node> hashAbertos = new HashMap<>();
    static Map<String, Node> hashFechados = new HashMap<>();
    static Map<String, Boolean> hashTerminal = new HashMap<>();
    
    //static int[][] listaInicial = new int[4][4];
    static int[][] nodeInicial = {{2,1,5,9}, {3,6,10,13}, {4,7,11,14}, {0,8,12,15}}; //9 Movimentos 
    //static int[][] listaInicial = {{6,5,13,0}, {1,7,9,14}, {2,8,10,15}, {3,4,11,12}}; //15 Movimentos  
    //static int[][] listaInicial = {{2,1,10,9}, {3,5,11,13}, {4,0,6,12}, {7,8,15,14}}; //21 Movimentos  
    //static int[][] listaInicial = {{2,1,5,0}, {7,9,10,13}, {6,4,3,15}, {8,11,12,14}}; //25 Movimentos  
    //static int[][] listaInicial = {{1,5,7,0}, {4,6,12,10}, {8,2,15,9}, {3,14,11,13}}; //39 Movimentos 
    //static int[][] listaInicial = {{9,13,12,8}, {0,5,7,14}, {1,11,15,4}, {6,10,2,3}}; //47 Movimentos
    static int[][] nodeTerminal = {{1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {4, 8, 12, 0}};
    
    static Node menor;
    static Node first;
    static List<Node> filhos;
    
    public static void main(String[] args){
        hashTerminal.put(chaveHashEstado(nodeTerminal), Boolean.TRUE);
        filhos = new ArrayList<>();
        first = new Node();
        first.setEstado(nodeInicial);
        first.setHeuristica(calculaHeuristica(first));
        first.setCustoG(0);
        first.setCustoF(first.getCustoG() + first.getHeuristica());
        first.setHash(chaveHashEstado(nodeInicial));
        aEstrela();
    }
    
    static void aEstrela(){
        treeAbertos.add(first);
        hashAbertos.put(first.getHash(), first);
        menor = treeAbertos.iterator().next();
        System.out.println(treeAbertos.isEmpty() + "\t " + !hashTerminal.containsKey(menor.getHash()));
        while(!treeAbertos.isEmpty() && !hashTerminal.containsKey(menor.getHash())){
            System.out.println("1");
            treeAbertos.remove(menor);
            hashAbertos.remove(menor.getHash());
            hashFechados.put(menor.getHash(), menor);
            criaFilhosNode(menor);
            for(Node filho: filhos){
                if(hashAbertos.containsKey(filho.getHash())){
                    Node igual = hashAbertos.get(filho.getHash());
                    if(filho.getCustoG() < igual.getCustoG()){
                        treeAbertos.remove(igual);
                        hashAbertos.remove(igual.getHash());
                    }
                } else { 
                    treeAbertos.add(filho);
                    hashAbertos.put(filho.getHash(), filho);
                }
                if(hashFechados.containsKey(filho.getHash())){
                    Node igual = hashFechados.get(filho.getHash());
                    if(filho.getCustoG() < igual.getCustoG()){
                        hashFechados.remove(igual.getHash());
                    }
                } else {hashFechados.put(filho.getHash(), filho);}
            }
            filhos.clear();
            menor = treeAbertos.iterator().next();
        }
        System.out.println(menor.getCustoG());
    }
    
    static int[][] copiaEstado(int[][] pai) {
        int[][] novoEstado = new int[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(pai[i], 0, novoEstado[i], 0, 4);
        }
        return novoEstado;
    }
    
    static String chaveHashEstado(int[][] estado){
        String chaveHash = "";
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                chaveHash.concat(Integer.toString(estado[i][j]));
                chaveHash.concat("$");
            }
        }
        return chaveHash;
    }
    
    static int calculaHeuristica(Node node) {
        return heuristica1(node);
        //return heuristica2(node);
        //return heuristica3(node);
        //return heuristica4(node);
        //return heuristica5(node);
    }
    
    static int heuristica1(Node node) {
        int heuristica = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((node.getEstado()[i][j] != nodeTerminal[i][j]) && (node.getEstado()[i][j] != 0)) {
                    heuristica++;
                }
            }
        }
        return heuristica;
    }
    
    static void criaFilhosNode(Node pai) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (pai.getEstado()[row][col] == 0) {
                    if (row != 0) {
                        Node cima = new Node();
                        cima.setEstado(copiaEstado(pai.getEstado()));
                        cima.getEstado()[row][col] = pai.getEstado()[row - 1][col];
                        cima.getEstado()[row - 1][col] = 0;
                        cima.setHeuristica(calculaHeuristica(cima));
                        cima.setCustoG(pai.getCustoG() + 1);
                        cima.setCustoF(cima.getHeuristica() + cima.getCustoG());
                        cima.setHash(chaveHashEstado(cima.getEstado()));
                        filhos.add(cima);
                    }
                    if (row != 3) {
                        Node baixo = new Node();
                        baixo.setEstado(copiaEstado(pai.getEstado()));
                        baixo.getEstado()[row][col] = pai.getEstado()[row + 1][col];
                        baixo.getEstado()[row + 1][col] = 0;
                        baixo.setHeuristica(calculaHeuristica(baixo));
                        baixo.setCustoG(pai.getCustoG() + 1);
                        baixo.setCustoF(baixo.getHeuristica() + baixo.getCustoG());
                        baixo.setHash(chaveHashEstado(baixo.getEstado()));
                        filhos.add(baixo);
                    }
                    if (col != 0) {
                        Node esq = new Node();
                        esq.setEstado(copiaEstado(pai.getEstado()));
                        esq.getEstado()[row][col] = pai.getEstado()[row][col - 1];
                        esq.getEstado()[row][col - 1] = 0;
                        esq.setHeuristica(calculaHeuristica(esq));
                        esq.setCustoG(pai.getCustoG() + 1);
                        esq.setCustoF(esq.getHeuristica() + esq.getCustoG());
                        esq.setHash(chaveHashEstado(esq.getEstado()));
                        filhos.add(esq);
                    }
                    if (col != 3) {
                        Node dir = new Node();
                        dir.setEstado(copiaEstado(pai.getEstado()));
                        dir.getEstado()[row][col] = pai.getEstado()[row][col + 1];
                        dir.getEstado()[row][col + 1] = 0;
                        dir.setHeuristica(calculaHeuristica(dir));
                        dir.setCustoG(pai.getCustoG() + 1);
                        dir.setCustoF(dir.getHeuristica() + dir.getCustoG());
                        dir.setHash(chaveHashEstado(dir.getEstado()));
                        filhos.add(dir);
                    }
                }
            }
        }
    }
}


class Node implements Comparable<Node> {

    private int[][] estado;
    private int heuristica;
    private int custoG;
    private int custoF;
    private String hash;

    //<editor-fold defaultstate="collapsed" desc=" Getters e Setters ">
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
    
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    //</editor-fold>
    
    //Para ordenar por função F
    @Override
    public int compareTo(Node o) {
        if (this.custoF <= o.getCustoF()) {
            if (Main3.hashTerminal.containsKey(o.hash)) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return 1;
        }
    }
}
