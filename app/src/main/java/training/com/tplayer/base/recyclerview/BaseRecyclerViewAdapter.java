package training.com.tplayer.base.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by hnc on 05/04/2017.
 */

public abstract class BaseRecyclerViewAdapter<E extends BaseEntity, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    private IRecyclerViewOnItemClickListener listener;

    public Context mContext;

    private ArrayList<E> mDatas = new ArrayList<>();

    public BaseRecyclerViewAdapter(Context context, IRecyclerViewOnItemClickListener listener) {
        this.listener = listener;
        this.mContext = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        onCreateViewHolderAdapter(parent, viewType);
        return null;
    }

    public abstract VH onCreateViewHolderAdapter(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        onBindViewHolderAdapter(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecyclerViewItemClick(v, getDataItem(position), position);
            }
        });
    }

    public abstract void onBindViewHolderAdapter(VH holder, int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setDatas(ArrayList<E> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(E entity) {
        mDatas.add(entity);
        notifyItemChanged(mDatas.indexOf(entity));
    }

    public void addData(E entity, int position) {
        mDatas.add(position, entity);
        notifyItemChanged(position);
    }

    public void updateData(E entity, int position) {
        mDatas.set(position, entity);
        notifyItemChanged(position);
    }

    public void removeData(E e) {
        int pos = mDatas.indexOf(e) - 1;
        mDatas.remove(e);
        notifyItemChanged(pos);
    }

    public void removeData(E e, int position) {
        mDatas.remove(e);
        notifyItemChanged(position);
    }

    public E getDataItem(int position) {
        return mDatas.get(position);
    }

    public int getSize() {
        return mDatas.size();
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void printDatasOfAdapter() {
        LogUtils.printLog(mDatas.toString());
    }

}