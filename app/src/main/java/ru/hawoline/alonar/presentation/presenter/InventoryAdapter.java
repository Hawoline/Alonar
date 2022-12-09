package ru.hawoline.alonar.presentation.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.hawoline.alonar.R;
import ru.hawoline.alonar.domain.personage.inventory.Bag;
import ru.hawoline.alonar.domain.personage.inventory.Inventory;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {
    private Inventory mInventory;

    public InventoryAdapter(Inventory inventory) {
        mInventory = inventory;
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_itemview, parent, false);
        return new InventoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        for (int i = 0; i < mInventory.getBags().size(); i++) {
            Bag bag = mInventory.getBags().get(i);
            if (position >= bag.getCapacity()) {
                position -= bag.getCapacity();
            } else {
                holder.getItemTextView().setText(bag.getItem(position).getName());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mInventory.getItemCount();
    }

    protected static class InventoryViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemTextView;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemTextView = itemView.findViewById(R.id.inventory_itemname_textview);
        }

        public TextView getItemTextView() {
            return mItemTextView;
        }
    }
}
