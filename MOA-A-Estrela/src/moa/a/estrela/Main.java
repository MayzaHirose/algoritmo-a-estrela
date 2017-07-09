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
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Mayza Yuri Hirose da Costa RA88739
 */
class Main {
    static final int HEURISTICA = 3; //Selecione aqui a Heurística a ser utilizada
    static Set<Node> treeAbertos = new TreeSet<>();
    static Map<String, Node> hashAbertos = new HashMap<>();
    static Map<String, Node> hashFechados = new HashMap<>();
    static Map<String, Boolean> hashTerminal = new HashMap<>();
    static Map<Integer,Coordenada> hashPosCorretaH3 = new HashMap<>();
    static int[][] nodeInicial = new int[4][4];
    //static int[][] nodeInicial = {{2,1,5,9}, {3,6,10,13}, {4,7,11,14}, {0,8,12,15}}; //9 Movimentos 
    //static int[][] nodeInicial = {{6,5,13,0}, {1,7,9,14}, {2,8,10,15}, {3,4,11,12}}; //15 Movimentos  
    //static int[][] nodeInicial = {{2,1,10,9}, {3,5,11,13}, {4,0,6,12}, {7,8,15,14}}; //21 Movimentos  
    //static int[][] nodeInicial = {{2,1,5,0}, {7,9,10,13}, {6,4,3,15}, {8,11,12,14}}; //25 Movimentos  
    //static int[][] nodeInicial = {{1,5,7,0}, {4,6,12,10}, {8,2,15,9}, {3,14,11,13}}; //39 Movimentos 
    //static int[][] nodeInicial = {{9,13,12,8}, {0,5,7,14}, {1,11,15,4}, {6,10,2,3}}; //47 Movimentos
    
    //static int[][] nodeInicial = {{5,13,6,10}, {1,7,2,9}, {4,3,15,14}, {8,0,11,12}}; // 1º 20 Movimentos
    //static int[][] nodeInicial = {{2,10,11,9}, {3,1,0,13}, {4,6,7,14}, {5,8,12,15}}; // 2º 27 Movimentos
    //static int[][] nodeInicial = {{5,9,13,10}, {2,6,14,15}, {1,4,7,12}, {0,3,11,8}}; // 3º 27 Movimentos
    //static int[][] nodeInicial = {{7,11,4,5}, {0,6,15,8}, {14,1,3,13}, {9,12,10,2}}; // 4º 57 Movimentos
    //static int[][] nodeInicial = {{5,10,9,14}, {7,3,13,6}, {1,15,0,12}, {8,2,4,11}}; // 5º 34 Movimentos
    //static int[][] nodeInicial = {{0,9,3,7}, {1,14,6,4}, {2,11,12,15}, {13,8,10,5}}; // 6º 56 Movimentos
    //static int[][] nodeInicial = {{3,9,0,7}, {2,1,6,5}, {11,13,4,12}, {8,14,15,10}}; // 7º 44 Movimentos
    //static int[][] nodeInicial = {{9,6,7,4}, {2,1,5,12}, {8,3,11,0}, {14,15,10,13}}; // 8º 51 Movimentos
    //static int[][] nodeInicial = {{2,9,4,5}, {0,7,11,12}, {14,6,3,13}, {1,8,15,10}}; // 9º 49 Movimentos
    //static int[][] nodeInicial = {{7,11,5,12}, {9,8,6,13}, {2,3,4,10}, {14,1,15,0}}; // 10º 50 Movimentos
    
    static int[][] nodeObjetivo = {{1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {4, 8, 12, 0}};
    
    static Node menor;
    static Node first;
    static List<Node> filhos;
    static Long start = System.currentTimeMillis();
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);;
        int num = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                num = scan.nextInt();
                nodeInicial[i][j] = num;
            }
        }
        if(HEURISTICA != 1 && HEURISTICA != 2){inicializaHashH3();}
        hashTerminal.put(chaveHashEstado(nodeObjetivo), Boolean.TRUE);
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
        boolean isNosAbertos;
        boolean isNosFechados;
        treeAbertos.add(first);
        hashAbertos.put(first.getHash(), first);      
        menor = treeAbertos.iterator().next();
                   
        while(!treeAbertos.isEmpty() && !hashTerminal.containsKey(menor.getHash())){
            treeAbertos.remove(menor);
            hashAbertos.remove(menor.getHash());
            hashFechados.put(menor.getHash(), menor);
            criaFilhosNode(menor);        
            for(Node filho: filhos){
                isNosAbertos = hashAbertos.containsKey(filho.getHash());
                isNosFechados = hashFechados.containsKey(filho.getHash());
                if(isNosAbertos){
                    Node igual = hashAbertos.get(filho.getHash());
                    if(filho.getCustoG() < igual.getCustoG()){
                        treeAbertos.remove(igual);
                        hashAbertos.remove(igual.getHash());
                        isNosAbertos = false;
                    }
                }
                if(isNosFechados){
                    Node igual = hashFechados.get(filho.getHash());
                    if(filho.getCustoG() < igual.getCustoG()){
                        hashFechados.remove(igual.getHash());
                        isNosFechados = false;
                    }
                }
                if(!isNosAbertos && !isNosFechados){ 
                    treeAbertos.add(filho);
                    hashAbertos.put(filho.getHash(), filho);
                }
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
        String chaveHash = new String();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                chaveHash = chaveHash.concat(Integer.toString(estado[i][j]));
                chaveHash = chaveHash.concat("$");
            }
        }
        return chaveHash;
    }
    
    static int calculaHeuristica(Node node) {
        switch(HEURISTICA){
            case 1:
                return heuristica1(node);
            case 2:
                return heuristica2(node);
            case 3:
                return heuristica3(node);
            case 4:
                return heuristica4(node);
            case 5:
                return heuristica5(node);
            default:
                return heuristica3(node);
        }
    }
    
    static int heuristica1(Node node) {
        int heuristica = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((node.getEstado()[i][j] != nodeObjetivo[i][j]) && (node.getEstado()[i][j] != 0)) {
                    heuristica++;
                }
            }
        }
        return heuristica;
    }
    
    static int heuristica2(Node node) {
        int heuristica = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j < 3) {
                    if ((node.getEstado()[j][i] + 1 != node.getEstado()[j + 1][i]) && (node.getEstado()[j][i] != 0)) {
                        heuristica++;
                    }
                } else {
                    if (i == 3) {
                        if (node.getEstado()[j][i] == 15) {
                            return heuristica;
                        } else {
                            return heuristica++;
                        }
                    }
                    if ((node.getEstado()[j][i] + 1 != node.getEstado()[0][i + 1]) && (node.getEstado()[j][i] != 0)) {
                        heuristica++;
                    }
                }
            }
        }
        return heuristica;
    }

    static int heuristica3(Node node) {
        int heuristica = 0;
        Coordenada coord;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (node.getEstado()[i][j] != nodeObjetivo[i][j] && node.getEstado()[i][j] != 0) {
                    coord = hashPosCorretaH3.get(node.getEstado()[i][j]);
                    heuristica += Math.abs(coord.x - i) + Math.abs(coord.y - j);
                }
            }
        }
        return heuristica;
    }

    static int heuristica4(Node node) {
        float heuristica = 0F;
        float p1 = 0.2F;
        float p2 = 0.1F;
        float p3 = 0.7F;
        heuristica = (p1 * heuristica1(node)) + (p2 * heuristica2(node)) + (p3 * heuristica3(node));
        return (int) heuristica;
    }

    static int heuristica5(Node node) {
        return Math.max(Math.max(heuristica1(node), heuristica2(node)), heuristica3(node));
    }
    
    static void inicializaHashH3(){
        int num=1;
        Coordenada coord = new Coordenada();
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                coord.x = y;
                coord.y = x;
                hashPosCorretaH3.put(num, coord);
                coord = new Coordenada();
                if(num==15) {num=0;}
                else num++;
            }
        }
    }
    
    static void criaFilhosNode(Node pai) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (pai.getEstado()[row][col] == 0) {
                    if ((row != 0) && (pai.veioDeBaixo == 0)) {
                        Node cima = new Node();
                        cima.setEstado(copiaEstado(pai.getEstado()));
                        cima.getEstado()[row][col] = pai.getEstado()[row - 1][col];
                        cima.getEstado()[row - 1][col] = 0;
                        cima.setHeuristica(calculaHeuristica(cima));
                        cima.setCustoG(pai.getCustoG() + 1);
                        cima.setCustoF(cima.getHeuristica() + cima.getCustoG());
                        cima.setHash(chaveHashEstado(cima.getEstado()));
                        cima.veioDeCima = -1;
                        filhos.add(cima);
                    }
                    if ((row != 3) && (pai.veioDeCima == 0)) {
                        Node baixo = new Node();
                        baixo.setEstado(copiaEstado(pai.getEstado()));
                        baixo.getEstado()[row][col] = pai.getEstado()[row + 1][col];
                        baixo.getEstado()[row + 1][col] = 0;
                        baixo.setHeuristica(calculaHeuristica(baixo));
                        baixo.setCustoG(pai.getCustoG() + 1);
                        baixo.setCustoF(baixo.getHeuristica() + baixo.getCustoG());
                        baixo.setHash(chaveHashEstado(baixo.getEstado()));
                        baixo.veioDeBaixo = -1;
                        filhos.add(baixo);
                    }
                    if ((col != 0) && (pai.veioDaEsq == 0)) {
                        Node esq = new Node();
                        esq.setEstado(copiaEstado(pai.getEstado()));
                        esq.getEstado()[row][col] = pai.getEstado()[row][col - 1];
                        esq.getEstado()[row][col - 1] = 0;
                        esq.setHeuristica(calculaHeuristica(esq));
                        esq.setCustoG(pai.getCustoG() + 1);
                        esq.setCustoF(esq.getHeuristica() + esq.getCustoG());
                        esq.setHash(chaveHashEstado(esq.getEstado()));
                        esq.veioDaDir = -1;
                        filhos.add(esq);
                    }
                    if ((col != 3) && (pai.veioDaDir == 0)) {
                        Node dir = new Node();
                        dir.setEstado(copiaEstado(pai.getEstado()));
                        dir.getEstado()[row][col] = pai.getEstado()[row][col + 1];
                        dir.getEstado()[row][col + 1] = 0;
                        dir.setHeuristica(calculaHeuristica(dir));
                        dir.setCustoG(pai.getCustoG() + 1);
                        dir.setCustoF(dir.getHeuristica() + dir.getCustoG());
                        dir.setHash(chaveHashEstado(dir.getEstado()));
                        dir.veioDaEsq = -1;
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
    int veioDeCima = 0;
    int veioDeBaixo = 0;
    int veioDaEsq = 0;
    int veioDaDir = 0;

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
    
    //Para ordenar por funcao F
    @Override
    public int compareTo(Node o) {
        if (hash.equals(o.hash)){           
            return 0;
        }
        if (this.custoF <= o.getCustoF()) {
            if (Main.hashTerminal.containsKey(o.hash)) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return 1;
        }
    }
}

//Para nao precisar percorrer a matriz e encontrar a posicao correta na heuritica 3
class Coordenada{
    public int x;
    public int y;
    
    public Coordenada(){       
    }

    public Coordenada(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int x(){ return x; }
    public int y(){ return y; }
}