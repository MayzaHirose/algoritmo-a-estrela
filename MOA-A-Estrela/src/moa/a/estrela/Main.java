/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moa.a.estrela;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author MayzaHirose
 */
public class Main {
    
    static Set<Node> estadosAbertos = new TreeSet<>();
    static Set<Node> estadosFechados = new TreeSet<>();
    static int[][] listaInicial = {{2,1,5,9}, {3,6,10,13}, {4,7,11,14}, {0,8,12,15}};
    //static int[][] listaInicial = {{1,5,9,13}, {2,6,10,14}, {3,7,11,0}, {4,8,12,15}};
    static int[][] listaFinal = {{1,5,9,13}, {2,6,10,14}, {3,7,11,15}, {4,8,12,0}};
    static Node inicio = new Node();
    static Node menor;
    
    public static void main(String[] args) {
        inicio.setPai(null);
        inicio.setEstado(listaInicial);
        inicio.setHeuristica(calculaHeuristica1(inicio));
        System.out.println(inicio.getHeuristica());
        inicio.setCustoG(0);
        inicio.setCustoF(inicio.getHeuristica() + inicio.getCustoG());
        System.out.println(inicio.getCustoF());
        inicio.setFilhos(filhosNode(inicio));
        aEstrela();
    }
    
    static void aEstrela (){
        estadosAbertos.add(inicio);        
        menor = estadosAbertos.iterator().next();
        while ((estadosAbertos.size() != 0) && !isEstadoFinal(menor)) { 
            /*Iterator q = estadosAbertos.iterator();
            while(q.hasNext()){
                Node q1 = (Node) q.next();
                if(isIguais(q1, menor)){
                    estadosAbertos.remove(q1);
                    System.out.println("moa.a.estrela.Main.aEstrela()");
                }
            }*/
            estadosAbertos.remove(menor);
            estadosFechados.add(menor);
            System.out.println(menor.getCustoF());
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++)
                    System.out.print(menor.getEstado()[i][j]);
            }
            if(menor.getFilhos() == null){
                menor.setFilhos(filhosNode(menor));
            }
            for(Node filho: menor.getFilhos()){
                //calcula G. No caso ja foi calculado qdo criou os filhos
                Iterator it = estadosAbertos.iterator();
                while(it.hasNext()){
                    Node atual = (Node) it.next();
                    if(isIguais(atual, filho)){
                        if(filho.getCustoG()<atual.getCustoG()){
                            estadosAbertos.remove(atual);    
                            System.out.println("saindo");
                            break;
                        }
                    }
                }
                Iterator it2 = estadosFechados.iterator();
                while(it2.hasNext()){
                    Node atual = (Node) it2.next();
                    if(isIguais(atual, filho)){
                        if(filho.getCustoG()<atual.getCustoG()){
                            estadosFechados.remove(atual);     
                            System.out.println("saindo2");
                            break;
                        }
                    }
                }
                estadosAbertos.add(filho);               
                menor = estadosAbertos.iterator().next();                         
            }
        }
        System.out.println("Movimentos = " + estadosAbertos.iterator().next().getCustoG());
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
    
    static List filhosNode(Node pai) {
        List<Node> filhos = new ArrayList<>();
        for (int row=0; row<4; row++) {
            for(int col=0; col<4; col++){
                if (pai.getEstado()[row][col] == 0){
                    boolean isBordaEsq = isBordaEsq(col);
                    boolean isBordaDir = isBordaDir(col);
                    boolean isBordaCima = isBordaCima(row);
                    boolean isBordaBaixo = isBordaBaixo(row);
                    if (isBordaEsq) {
                        Node dir = new Node();
                        dir.setPai(pai);
                        dir.setEstado(copiaEstado(pai.getEstado()));
                        dir.getEstado()[row][col] = pai.getEstado()[row][col+1];
                        dir.getEstado()[row][col+1] = 0;
                        dir.setHeuristica(calculaHeuristica1(dir));
                        dir.setCustoG(pai.getCustoG() + 1);
                        dir.setCustoF(dir.getHeuristica() + dir.getCustoG());
                        filhos.add(dir);
                        if (isBordaCima) {
                            Node baixo = new Node();
                            baixo.setPai(pai);
                            baixo.setEstado(copiaEstado(pai.getEstado()));
                            baixo.getEstado()[row][col] = pai.getEstado()[row+1][col];
                            baixo.getEstado()[row+1][col] = 0;
                            baixo.setHeuristica(calculaHeuristica1(baixo));
                            baixo.setCustoG(pai.getCustoG() + 1);
                            baixo.setCustoF(baixo.getHeuristica() + baixo.getCustoG());
                            filhos.add(baixo);
                        } else if (isBordaBaixo) {
                            Node cima = new Node();
                            cima.setPai(pai);
                            cima.setEstado(copiaEstado(pai.getEstado()));
                            cima.getEstado()[row][col] = pai.getEstado()[row-1][col];
                            cima.getEstado()[row-1][col] = 0;
                            cima.setHeuristica(calculaHeuristica1(cima));
                            cima.setCustoG(pai.getCustoG() + 1);
                            cima.setCustoF(cima.getHeuristica() + cima.getCustoG());
                            filhos.add(cima);
                        } else {
                            Node cima = new Node();
                            cima.setPai(pai);
                            cima.setEstado(copiaEstado(pai.getEstado()));
                            cima.getEstado()[row][col] = pai.getEstado()[row-1][col];
                            cima.getEstado()[row-1][col] = 0;
                            cima.setHeuristica(calculaHeuristica1(cima));
                            cima.setCustoG(pai.getCustoG() + 1);
                            cima.setCustoF(cima.getHeuristica() + cima.getCustoG());
                            filhos.add(cima);
                            
                            Node baixo = new Node();
                            baixo.setPai(pai);
                            baixo.setEstado(copiaEstado(pai.getEstado()));
                            baixo.getEstado()[row][col] = pai.getEstado()[row+1][col];
                            baixo.getEstado()[row+1][col] = 0;
                            baixo.setHeuristica(calculaHeuristica1(baixo));
                            baixo.setCustoG(pai.getCustoG() + 1);
                            baixo.setCustoF(baixo.getHeuristica() + baixo.getCustoG());
                            filhos.add(baixo);
                        }
                    } else {
                        Node esq = new Node();
                        esq.setPai(pai);
                        esq.setEstado(copiaEstado(pai.getEstado()));
                        esq.getEstado()[row][col] = pai.getEstado()[row][col-1];
                        esq.getEstado()[row][col-1] = 0;
                        esq.setHeuristica(calculaHeuristica1(esq));
                        esq.setCustoG(pai.getCustoG() + 1);
                        esq.setCustoF(esq.getHeuristica() + esq.getCustoG());
                        filhos.add(esq);                      
                        if (isBordaCima) {
                            Node baixo = new Node();
                            baixo.setPai(pai);
                            baixo.setEstado(copiaEstado(pai.getEstado()));
                            baixo.getEstado()[row][col] = pai.getEstado()[row+1][col];
                            baixo.getEstado()[row+1][col] = 0;
                            baixo.setHeuristica(calculaHeuristica1(baixo));
                            baixo.setCustoG(pai.getCustoG() + 1);
                            baixo.setCustoF(baixo.getHeuristica() + baixo.getCustoG());
                            filhos.add(baixo);
                        } else if (isBordaBaixo) {
                            Node cima = new Node();
                            cima.setPai(pai);
                            cima.setEstado(copiaEstado(pai.getEstado()));
                            cima.getEstado()[row][col] = pai.getEstado()[row-1][col];
                            cima.getEstado()[row-1][col] = 0;
                            cima.setHeuristica(calculaHeuristica1(cima));
                            cima.setCustoG(pai.getCustoG() + 1);
                            cima.setCustoF(cima.getHeuristica() + cima.getCustoG());
                            filhos.add(cima);
                        } else {
                            Node cima = new Node();
                            cima.setPai(pai);
                            cima.setEstado(copiaEstado(pai.getEstado()));
                            cima.getEstado()[row][col] = pai.getEstado()[row-1][col];
                            cima.getEstado()[row-1][col] = 0;
                            cima.setHeuristica(calculaHeuristica1(cima));
                            cima.setCustoG(pai.getCustoG() + 1);
                            cima.setCustoF(cima.getHeuristica() + cima.getCustoG());
                            filhos.add(cima);
                            
                            Node baixo = new Node();
                            baixo.setPai(pai);
                            baixo.setEstado(copiaEstado(pai.getEstado()));
                            baixo.getEstado()[row][col] = pai.getEstado()[row+1][col];
                            baixo.getEstado()[row+1][col] = 0;
                            baixo.setHeuristica(calculaHeuristica1(baixo));
                            baixo.setCustoG(pai.getCustoG() + 1);
                            baixo.setCustoF(baixo.getHeuristica() + baixo.getCustoG());
                            filhos.add(baixo);
                        }
                        if (!isBordaDir){
                            Node dir = new Node();
                            dir.setPai(pai);
                            dir.setEstado(copiaEstado(pai.getEstado()));
                            dir.getEstado()[row][col] = pai.getEstado()[row][col+1];
                            dir.getEstado()[row][col+1] = 0;
                            dir.setHeuristica(calculaHeuristica1(dir));
                            dir.setCustoG(pai.getCustoG() + 1);
                            dir.setCustoF(dir.getHeuristica() + dir.getCustoG());
                            filhos.add(dir);
                        }
                    }
                return filhos;
                }
            }
        }
        return filhos;
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Métodos isBorda... ">
    static boolean isBordaDir(int col) {
        return (col == 3);
    }

    static boolean isBordaEsq(int col) {
        return (col == 0);
    }

    static boolean isBordaCima(int row) {
        return (row == 0);
    }

    static boolean isBordaBaixo(int row) {
        return (row == 3);
    }  
    //</editor-fold>
    
    static int[][] copiaEstado(int[][] pai){
        int[][] novoEstado = new int[4][4];
        for(int i=0; i<4; i++){
            System.arraycopy(pai[i], 0, novoEstado[i], 0, 4);
        }
        return novoEstado;
    }
    
    static boolean isEstadoFinal(Node node){
        return Arrays.deepEquals(node.getEstado(), listaFinal);
    }
    
    static boolean isIguais(Node aberto, Node filho){
        return Arrays.deepEquals(aberto.getEstado(), filho.getEstado());
    }

}

class Node implements Comparable<Node>{
 
    private Node pai;
    private int[][] estado;
    private int heuristica;
    private int custoG;
    private int custoF;
    private List<Node> filhos;

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

    public Node getPai() {
        return pai;
    }

    public void setPai(Node pai) {
        this.pai = pai;
    }

    public List<Node> getFilhos() {
        return filhos;
    }

    public void setFilhos(List<Node> filhos) {
        this.filhos = filhos;
    }
    //</editor-fold>
    
    @Override
    public int compareTo(Node o) {
        if (Arrays.deepEquals(this.estado, o.estado)){
            return 0;
        }
        else if (this.custoF < o.getCustoF()) {
            return -1;
        }
        else {
            return 1;
        }      
    }
}
