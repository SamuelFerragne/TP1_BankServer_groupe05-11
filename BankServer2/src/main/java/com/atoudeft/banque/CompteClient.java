package com.atoudeft.banque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompteClient implements Serializable {
    private String numero;
    private String nip;
    private List<CompteBancaire> comptes;

    /**
     * Crée un compte-client avec un numéro et un nip.
     *
     * @param numero le numéro du compte-client
     * @param nip le nip
     */
    public CompteClient(String numero, String nip) {
        this.numero = numero;
        this.nip = nip;
        comptes = new ArrayList<>();
    }
    public String toString() {
        String str = numero+":"+nip + "[";
        for (CompteBancaire c:comptes)
            str += c + " | ";
        return str+"]";
    }
    /**
     * Ajoute un compte bancaire au compte-client.
     *
     * @param compte le compte bancaire
     * @return true si l'ajout est réussi
     */
    public boolean ajouter(CompteBancaire compte) {
        return this.comptes.add(compte);
    }
    /**
     * Retourne le numéro du compte-client.
     * @return numéro du compte-client
     */
    public String getNumero() {
        return numero;
    }
    public List<CompteBancaire> getComptes() {
        return comptes;
    }

    public String getNip() {
        return nip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompteClient)) return false;
        CompteClient that = (CompteClient) o;
        return Objects.equals(numero, that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }

    public CompteBancaire getCompteCheque() {
        for (CompteBancaire cpt:comptes) {
            if (cpt.getType()==TypeCompte.CHEQUE)
                return cpt;
        }
        return null;
    }

    public CompteBancaire getCompteEpargne() {
        for (CompteBancaire cpt:comptes) {
            if (cpt.getType()==TypeCompte.EPARGNE)
                return cpt;
        }
        return null;
    }
}