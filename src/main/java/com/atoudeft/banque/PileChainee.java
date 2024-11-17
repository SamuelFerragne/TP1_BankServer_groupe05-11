import java.io.Serializable;

public class PileChainee<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Noeud<T> sommet;
    private Noeud<T> dernier;

    private static class Noeud<T> implements Serializable {
        private static final long serialVersionUID = 1L;

        T valeur;
        Noeud<T> suivant;
        Noeud<T> precedent;

        Noeud(T valeur){
            this.valeur = valeur;
            this.suivant = null;
            this. precedent = null;
        }
    }

    public void empiler(T valeur){
        Noeud<T> nouveauNoeud = new Noeud<>(valeur);
        if(sommet == null){
            sommet = nouveauNoeud;
            dernier = nouveauNoeud;
        }else{
            nouveauNoeud.suivant = sommet;
            sommet.precedent = nouveauNoeud;
            sommet = nouveauNoeud;
        }
    }

    public T depiler(){
        if (sommet== null){
            return null;
        }
        T valeur = sommet.valeur;
        sommet = sommet.suivant;

        if(sommet != null){
            sommet.precedent = null;
        } else {
            dernier = null;
        }

        return valeur;
    }

    public boolean estVide(){
        return sommet==null;
    }

    public T getDernier(){
        if(dernier == null){
            return null;
        }
        return dernier.valeur;
    }

    public T getSommet(){
        if(sommet == null){
            return null;
        }
        return sommet.valeur;
    }
}