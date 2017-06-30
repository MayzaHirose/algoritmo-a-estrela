/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moa.a.estrela;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mayza
 */
public class Main {

    public static List<Node> estadosAbertos = new ArrayList<>();
    public static List<Node> estadosFechados = new ArrayList<>();
    public static List<Integer> listaInicial = new ArrayList<>(Arrays.asList(2, 1, 5, 9, 3, 6, 10, 13, 4, 7, 11, 14, 0, 8, 12, 15));
    public static List<Integer> listaFinal = new ArrayList<>(Arrays.asList(1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 0));
    int funcaoF;
    public static Node menorEstado;

    public static void main(String[] args) {
        aEstrela(listaInicial);
    }

    public static void aEstrela(List listaInicial) {
        Node estadoInicial = new Node();
        estadosAbertos.add(estadoInicial);
        estadoInicial.setEstado(listaInicial);
        estadoInicial.setHeuristica(calculaHeuristica(estadoInicial));
        estadoInicial.setCusto(0);
        estadoInicial.setPai(null);
        estadoInicial.setFilhos(filhosNode(estadoInicial));
        while ((estadosAbertos.size() != 0)) {
            menorEstado = menorHeuristica();
            estadosFechados.add(menorEstado);
        }
        /*insere o node inicial na lista
        para cada estado filho possivel, poe no aberto com cada um tendo o node pai
        remove o node pai dos abertos e poe nos fechados
        calcula melhor nodo a ser percorrido (calcula as heuristicas)*/
    }

    public static int calculaHeuristica(Node node) {
        int heuristica = 0;
        for (int i = 0; i < node.getEstado().size(); i++) {
            if (node.getEstado().get(i) != listaFinal.get(i)) {
                heuristica++;
                node.setHeuristica(heuristica);
            }
        }
        return heuristica;
    }

    public static Node menorHeuristica() {

        //estadosAbertos.
        return null;
    }

    public static List filhosNode(Node estado) {
        List<Node> filhos = new ArrayList();
        int vazio = posVazio(estado);
        boolean isBordaEsq = isBordaEsq(vazio);
        boolean isBordaDir = isBordaDir(vazio);
        boolean isBordaCima = isBordaCima(vazio);
        boolean isBordaBaixo = isBordaBaixo(vazio);
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

    public static int posVazio(Node estado) {
        int pos = 0;
        for (pos = 0; pos < (estado.getEstado().size()); pos++) {
            if (estado.getEstado().get(pos) == 0) {
                break;
            }
        }
        return pos;
    }

    public static boolean isBordaDir(int posZero) {
        return (((posZero + 1) % 4) == 0);
    }

    public static boolean isBordaEsq(int posZero) {
        return (((posZero) % 4) == 0);
    }

    public static boolean isBordaCima(int posZero) {
        return ((posZero + 4) >= 16);
    }

    public static boolean isBordaBaixo(int posZero) {
        return (posZero < 4);
    }

    /*List<Integer> listaInicial = new ArrayList<>();
        listaInicial.add(2);
        listaInicial.add(1);
        listaInicial.add(5);
        listaInicial.add(9);
        listaInicial.add(3);
        listaInicial.add(6);
        listaInicial.add(10);
        listaInicial.add(13);
        listaInicial.add(4);
        listaInicial.add(7);
        listaInicial.add(11);
        listaInicial.add(14);
        listaInicial.add(0);
        listaInicial.add(8);
        listaInicial.add(12);
        listaInicial.add(15);*/
}

class Node implements Comparable<Node> {

    private Node pai;
    private List<Node> filhos;
    private int heuristica;
    private int custo;
    private List<Integer> estado = new ArrayList<>();

    public int getHeuristica() {
        return heuristica;
    }

    public void setHeuristica(int heuristica) {
        this.heuristica = heuristica;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public List<Integer> getEstado() {
        return estado;
    }

    public void setEstado(List<Integer> estado) {
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

    @Override
    public int compareTo(Node o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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

}
