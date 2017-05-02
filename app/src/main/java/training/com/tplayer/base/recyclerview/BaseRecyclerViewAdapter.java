package training.com.tplayer.base.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 05/04/2017.
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
        return onCreateViewHolderAdapter(parent, viewType);
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

    public void setDatas(List<E> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addAll(List<E> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(E entity) {
        mDatas.add(entity);
        notifyItemChanged(mDatas.size());
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

    public void notifyItem(E e){
        boolean contains = mDatas.contains(e);
        if (contains){
            updateData(e,mDatas.indexOf(e));
        }
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

    public List<E> getDatas() {
        return mDatas;
    }

    public void printDatasOfAdapter() {
        LogUtils.printLog(mDatas.toString());
    }

}
