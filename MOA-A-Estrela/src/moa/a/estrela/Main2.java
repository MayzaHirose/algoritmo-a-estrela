///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package moa.a.estrela;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.PriorityQueue;
//import java.util.Scanner;
//import java.util.Set;
//import java.util.TreeMap;
//import java.util.TreeSet;
//
///**
// *
// * @author MayzaHirose
// */
//class Main2 {
//
//    static PriorityQueue<Node> estadosAbertos = new PriorityQueue<Node>();
//    //static PriorityQueue<Node> abertos = new PriorityQueue<>();
//    static ArrayList<Node> estadosFechados = new ArrayList<Node>();
//    //static Map<int[][], Node> hashAbertos = new HashMap<>();
//    //static Map<int[][], Node> hashFechados = new HashMap<>();
//    //static Map<int[][], Boolean> hashTerminal = new HashMap<>();
//    //static int[][] listaInicial = new int[4][4];
////    static int[][] listaInicial = {{2, 1, 5, 9}, {3, 6, 10, 13}, {4, 7, 11, 14}, {0, 8, 12, 15}}; //9 Movimentos  Heuristicas OK = 1 3 4 5
////    static int[][] listaInicial = {{6,5,13,0}, {1,7,9,14}, {2,8,10,15}, {3,4,11,12}}; //15 Movimentos  Heuristicas OK = 1 2 3 4 5
////    static int[][] listaInicial = {{2,1,10,9}, {3,5,11,13}, {4,0,6,12}, {7,8,15,14}}; //21 Movimentos  Heuristicas OK = 1 3 4 5
//   static int[][] listaInicial = {{2,1,5,0}, {7,9,10,13}, {6,4,3,15}, {8,11,12,14}}; //25 Movimentos  Heuristicas OK = 1 3 4 5
//    //static int[][] listaInicial = {{1, 5, 7, 0}, {4, 6, 12, 10}, {8, 2, 15, 9}, {3, 14, 11, 13}}; //39 Movimentos  Heuristicas OK = 
////    static int[][] listaInicial = {{9,13,12,8}, {0,5,7,14}, {1,11,15,4}, {6,10,2,3}}; //47 Movimentos  Heuristicas OK = 
//    //static int[][] listaInicial = {{15,11,7,3}, {14,10,6,2}, {13,9,5,1}, {12,8,4,0}}; //MAX Movimentos  Heuristicas OK = 
//    static int[][] listaFinal = {{1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {4, 8, 12, 0}};
//
//    static Node inicio = new Node();
//    static Node menor;
//    static List<Node> filhos = new ArrayList<>();
//
//    public static void main(String[] args) {
////        hashTerminal.put(listaFinal, true);
////        Scanner scan = new Scanner(System.in);;
////        int num = 0;
////        for (int i = 0; i < 4; i++) {
////            for (int j = 0; j < 4; j++) {
////                num = scan.nextInt();
////                listaInicial[i][j] = num;
////            }
////        }
//        inicio.setEstado(listaInicial);
//        // inicio.setHeuristica(calculaHeuristica(inicio));
//        inicio.setCustoG(0);
////        inicio.setCustoF(inicio.getHeuristica() + inicio.getCustoG());
//        //inicio.setFilhos(filhosNode(inicio));
//        filhos = filhosNode(inicio);
//        aEstrela();
//    }
//
//    static void aEstrela() {
//        int i = 0;
//        estadosAbertos.add(inicio);
//        menor = estadosAbertos.poll();
//        while (menor != null) {
//            if (isEstadoFinal(menor)) {
//                System.out.println(menor.getCustoG());
//                return;
//            }
//            estadosFechados.add(menor);
////            if (filhos.isEmpty()) {
//            filhosNode(menor);
////            }
//            //for(Node filho: menor.getFilhos()){
//            for (Node filho : filhos) {
//                i++;
//                /*if (estadosAbertos.contains(filho)) {
//                } else if (estadosFechados.contains(filho)) {
////                    System.out.println("aqui");
////
//                } else {
////                    System.out.println("aqui2");
//                    estadosAbertos.add(filho);
//                }*/
//                estadosAbertos.add(filho);
//                //menor = abertos.iterator().next();
//            }
//            filhos.clear();
//            menor = estadosAbertos.poll();
//        }
//    }
//
//    static int calculaHeuristica(Node node) {
////        return heuristica1(node);
//        //return heuristica2(node);
//        return heuristica3(node);
//        //return heuristica4(node);
//        //return heuristica5(node);
//    }
//
//    public static void printaMatriz(int[][] matriz) {
//        for (int i = 0; i < matriz.length; i++) {
//            for (int j = 0; j < matriz[i].length; j++) {
//                System.out.print("\t " + matriz[i][j]);
//            }
//            System.out.println("");
//        }
//        System.out.println("");
//    }
//
//    static int heuristica1(Node node) {
//        int heuristica = 0;
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                if ((node.getEstado()[i][j] != listaFinal[i][j]) && (node.getEstado()[i][j] != 0)) {
//                    heuristica++;
//                }
//            }
//        }
//        return heuristica;
//    }
//
//    static int heuristica2(Node node) {
//        int heuristica = 0;
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                if (j < 3) {
//                    if ((node.getEstado()[j][i] + 1 != node.getEstado()[j + 1][i]) && (node.getEstado()[j][i] != 0)) {
//                        heuristica++;
//                    }
//                } else {
//                    if (i == 3) {
//                        if (node.getEstado()[j][i] == 15) {
//                            return heuristica;
//                        } else {
//                            return heuristica++;
//                        }
//                    }
//                    if ((node.getEstado()[j][i] + 1 != node.getEstado()[0][i + 1]) && (node.getEstado()[j][i] != 0)) {
//                        heuristica++;
//                    }
//                }
//            }
//        }
//        return heuristica;
//    }
//
//    static int heuristica3(Node node) {
////        Main.printaMatriz(node.getEstado());
////        Main.printaMatriz(listaFinal);
//        int heuristica = 0;
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                if (node.getEstado()[i][j] != listaFinal[i][j] && listaFinal[i][j] != 0) {
//                    short coluna2 = (short) Math.floor(node.getEstado()[i][j] / 4.1);
//                    short linha2 = (short) (Math.floor((node.getEstado()[i][j] - 1) % 4));
//
//                    short hLinha = (short) (Math.abs(linha2 - i) + Math.abs(coluna2 - j));
//                    heuristica += hLinha;
//                    
//                    
////                    for (int k = 0; k < 4; k++) {
////                        for (int l = 0; l < 4; l++) {
//////                            tava k l    i j tendi
////                            if (node.getEstado()[i][j] == listaFinal[k][l]) {
////                                int h = (Math.abs(k - i)) + (Math.abs(l - j));
//////                                System.out.println(node.getEstado()[i][j] +"\t correto" + node.getEstado()[k][l] + "\t h:" + h);
////                                heuristica += Math.abs(k - i) + Math.abs(l - j);
////                                break;
////                            }
////                        }
////                    }
//                }
//            }
//        }
////        System.out.println(heuristica);
////        System.exit(0);
//        return heuristica;
//    }
//
//    static int heuristica4(Node node) {
//        float heuristica = 0;
//        float p1 = 1 / 3F;
//        float p2 = 1 / 3F;
//        float p3 = 1 / 3F;
//        heuristica = (p1 * heuristica1(node)) + (p2 * heuristica2(node)) + (p3 * heuristica3(node));
//
//        return (int) heuristica;
//    }
//
//    static int heuristica5(Node node) {
//        int heuristica, h2, h3;
//        heuristica = heuristica1(node);
//        h2 = heuristica2(node);
//        h3 = heuristica3(node);
//        if (h2 > heuristica) {
//            heuristica = h2;
//        }
//        if (h3 > heuristica) {
//            heuristica = h3;
//        }
//        return heuristica;
//    }
//
//    static List<Node> filhosNode(Node pai) {
//        //List<Node> filhos = new ArrayList<Node>();
//        for (int row = 0; row < 4; row++) {
//            for (int col = 0; col < 4; col++) {
//                if (pai.getEstado()[row][col] == 0) {
//                    if (row != 0) {
//                        Node cima = new Node();
//                        //cima.setPai(pai);
//                        cima.setEstado(copiaEstado(pai.getEstado()));
//                        cima.getEstado()[row][col] = pai.getEstado()[row - 1][col];
//                        cima.getEstado()[row - 1][col] = 0;
//                        cima.setHeuristica(calculaHeuristica(cima));
//                        cima.setCustoG(pai.getCustoG() + 1);
//                        cima.setCustoF(cima.getHeuristica() + cima.getCustoG());
//                        filhos.add(cima);
//                    }
//                    if (row != 3) {
//                        Node baixo = new Node();
//                        //baixo.setPai(pai);
//                        baixo.setEstado(copiaEstado(pai.getEstado()));
//                        baixo.getEstado()[row][col] = pai.getEstado()[row + 1][col];
//                        baixo.getEstado()[row + 1][col] = 0;
//                        baixo.setHeuristica(calculaHeuristica(baixo));
//                        baixo.setCustoG(pai.getCustoG() + 1);
//                        baixo.setCustoF(baixo.getHeuristica() + baixo.getCustoG());
//                        filhos.add(baixo);
//                    }
//                    if (col != 0) {
//                        Node esq = new Node();
//                        //esq.setPai(pai);
//                        esq.setEstado(copiaEstado(pai.getEstado()));
//                        esq.getEstado()[row][col] = pai.getEstado()[row][col - 1];
//                        esq.getEstado()[row][col - 1] = 0;
//                        esq.setHeuristica(calculaHeuristica(esq));
//                        esq.setCustoG(pai.getCustoG() + 1);
//                        esq.setCustoF(esq.getHeuristica() + esq.getCustoG());
//                        filhos.add(esq);
//                    }
//                    if (col != 3) {
//                        Node dir = new Node();
//                        //dir.setPai(pai);
//                        dir.setEstado(copiaEstado(pai.getEstado()));
//                        dir.getEstado()[row][col] = pai.getEstado()[row][col + 1];
//                        dir.getEstado()[row][col + 1] = 0;
//                        dir.setHeuristica(calculaHeuristica(dir));
//                        dir.setCustoG(pai.getCustoG() + 1);
//                        dir.setCustoF(dir.getHeuristica() + dir.getCustoG());
//                        filhos.add(dir);
//                    }
//
//                    return filhos;
//                }
//            }
//        }
//        return filhos;
//    }
//
//    static int[][] copiaEstado(int[][] pai) {
//        int[][] novoEstado = new int[4][4];
//        for (int i = 0; i < 4; i++) {
//            System.arraycopy(pai[i], 0, novoEstado[i], 0, 4);
//        }
//        return novoEstado;
//    }
//
//    static boolean isEstadoFinal(Node node) {
//        return Arrays.deepEquals(node.getEstado(), listaFinal);
//    }
//
//    static boolean isIguais(Node aberto, Node filho) {
//        return Arrays.deepEquals(aberto.getEstado(), filho.getEstado());
//    }
//
//}
//
//class Node implements Comparable<Node> {
//
//    //private Node pai;
//    private int[][] estado;
//    private int heuristica;
//    private int custoG;
//    private int custoF;
//    //private List<Node> filhos;]
//    int[][] listaFinal = {{1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {4, 8, 12, 0}};
//
//    //<editor-fold defaultstate="collapsed" desc=" Getters e Setters ">
//    public int getHeuristica() {
//        return heuristica;
//    }
//
//    public void setHeuristica(int heuristica) {
//        this.heuristica = heuristica;
//    }
//
//    public int getCustoG() {
//        return custoG;
//    }
//
//    public void setCustoG(int custoG) {
//        this.custoG = custoG;
//    }
//
//    public int getCustoF() {
//        return custoF;
//    }
//
//    public void setCustoF(int custoF) {
//        this.custoF = custoF;
//    }
//
//    public int[][] getEstado() {
//        return estado;
//    }
//
//    public void setEstado(int[][] estado) {
//        this.estado = estado;
//    }
//
//    /*public Node getPai() {
//        return pai;
//    }
//
//    public void setPai(Node pai) {
//        this.pai = pai;
//    }
//
//    public List<Node> getFilhos() {
//        return filhos;
//    }
//    
//    
//    
//
//    public void setFilhos(List<Node> filhos) {
//        this.filhos = filhos;
//    }*/
//    //</editor-fold>
//    //Para não ter estado repetido, manter ordenado e priorizar o estado terminal
//    @Override
//    public boolean equals(Object obj) {
//        Node n = (Node) obj;
////        return chaveHash.equals(n.getChaveHash());
//        return Arrays.deepEquals(estado, n.getEstado());
////        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public int compareTo(Node o) {
//        /*if (Arrays.deepEquals(estado, o.estado)){
//            
//            return 0;
//        }*/
//        return this.custoF - o.custoF;
////        if (this.custoF <= o.getCustoF()) {
////            if (Arrays.deepEquals(o.getEstado(), listaFinal)) {
////                return 1;
////            } else {
////                return -1;
////            }
////        } else {
////            return 1;
////        }
//    }
//
//}
