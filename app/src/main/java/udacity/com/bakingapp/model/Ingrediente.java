package udacity.com.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingrediente implements Parcelable {

    @SerializedName("quantity")
    @Expose
    private Double quantidade;
    @SerializedName("measure")
    @Expose
    private String medida;
    @SerializedName("ingredient")
    @Expose
    private String ingrediente;

    protected Ingrediente(Parcel in) {
        quantidade = in.readDouble();
        medida = in.readString();
        ingrediente = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantidade);
        dest.writeString(medida);
        dest.writeString(ingrediente);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingrediente> CREATOR = new Creator<Ingrediente>() {
        @Override
        public Ingrediente createFromParcel(Parcel in) {
            return new Ingrediente(in);
        }

        @Override
        public Ingrediente[] newArray(int size) {
            return new Ingrediente[size];
        }
    };

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }
}