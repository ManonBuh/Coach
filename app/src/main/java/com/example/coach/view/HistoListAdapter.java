package com.example.coach.view;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coach.model.Profil;
import com.example.coach.R;
import com.example.coach.contract.IHistoView;
import com.example.coach.presenter.HistoPresenter;
import com.example.coach.api.ICallbackApi;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//Cette classe sert à gérer l'affichage de la liste (RecyclerView(historique))
public class HistoListAdapter extends RecyclerView.Adapter<HistoListAdapter.ViewHolder> {

    // Liste des profils à afficher
    private final List<Profil> profils;

    // vue de l'historique
    private final IHistoView vue;

    // Constructeur avec la liste et la vue
    public HistoListAdapter(List<Profil> profils, IHistoView vue) {
        this.profils = profils;
        this.vue = vue;
    }

    // Crée une ligne de la liste
    @NonNull
    @Override
    public HistoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext(); // récupère le contexte
        LayoutInflater inflater = LayoutInflater.from(context); // prépare le layout

        // crée une vue à partir du XML de la ligne
        View view = inflater.inflate(R.layout.layout_liste_histo, parent, false);

        // retourne une ligne (ViewHolder)
        return new ViewHolder(view);
    }

    // Remplit une ligne avec des données
    @Override
    public void onBindViewHolder(@NonNull HistoListAdapter.ViewHolder holder, int position) {

        // récupère l'img du profil
        Double img = profils.get(position).getImg();
        String imgFormate = String.format(Locale.getDefault(), "%.01f", img);
        holder.txtListIMG.setText(imgFormate);

        // récupère la date du profil
        Date dateMesure = profils.get(position).getDateMesure();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String dateFormatee = sdf.format(dateMesure);
        holder.txtListDate.setText(dateFormatee);
    }

    // Donne le nombre de lignes
    @Override
    public int getItemCount() {
        return profils == null ? 0 : profils.size(); // nombre de profils
    }

    // Représente UNE ligne
    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView txtListDate;
        public final TextView txtListIMG;
        public final ImageButton btnListSuppr;
        // presenter pour la suppression
        private final HistoPresenter presenter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtListDate = itemView.findViewById(R.id.txtListDate);
            txtListIMG = itemView.findViewById(R.id.txtListIMG);
            btnListSuppr = itemView.findViewById(R.id.btnListSuppr);

            btnListSuppr.setOnClickListener(v -> btnListSuppr_clic());

            // clic sur la ligne
            txtListDate.setOnClickListener(v -> ligne_clic());
            txtListIMG.setOnClickListener(v -> ligne_clic());

            presenter = new HistoPresenter(vue);
        }

        // Gère le clic sur la ligne
        private void ligne_clic() {

            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                presenter.transfertProfil(profils.get(position));
            }
        }

        // Gère le clic sur la croix
        private void btnListSuppr_clic() {

            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {

                presenter.supprProfil(profils.get(position), new ICallbackApi<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        profils.remove(position);      // enlève le profil de la liste
                        notifyItemRemoved(position);   // met à jour l'affichage
                    }

                    @Override
                    public void onError() {
                        // rien pour l'instant
                    }
                });
            }
        }
    }
}
