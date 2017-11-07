package udacity.com.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Receita extends Object implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String nome;
    @SerializedName("ingredients")
    @Expose
    private List<Ingrediente> ingredientes = new ArrayList<>();
    @SerializedName("steps")
    @Expose
    private List<Passos> passos = new ArrayList<>();
    @SerializedName("servings")
    @Expose
    private Integer porcao;
    @SerializedName("image")
    @Expose
    private String imagem;

    protected Receita(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        ingredientes = in.createTypedArrayList(Ingrediente.CREATOR);
        passos = in.createTypedArrayList(Passos.CREATOR);
        imagem = in.readString();
    }

    public static final Creator<Receita> CREATOR = new Creator<Receita>() {
        @Override
        public Receita createFromParcel(Parcel in) {
            return new Receita(in);
        }

        @Override
        public Receita[] newArray(int size) {
            return new Receita[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<Passos> getPassos() {
        return passos;
    }

    public void setPassos(List<Passos> passos) {
        this.passos = passos;
    }

    public Integer getPorcao() {
        return porcao;
    }

    public void setPorcao(Integer porcao) {
        this.porcao = porcao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeTypedList(ingredientes);
        dest.writeTypedList(passos);
        dest.writeString(imagem);
    }
}
