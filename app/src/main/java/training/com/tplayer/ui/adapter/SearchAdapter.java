package training.com.tplayer.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.recyclerview.BaseRecyclerViewAdapter;
import training.com.tplayer.base.recyclerview.BaseViewHolder;
import training.com.tplayer.base.recyclerview.IRecyclerViewOnItemClickListener;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.ui.entity.ItemSearchEntity;
import training.com.tplayer.ui.search.fragment.SearchFragment;
import training.com.tplayer.utils.ImageUtils;

/**
 * Created by hnc on 15/05/2017.
 */

public class SearchAdapter extends BaseRecyclerViewAdapter<ItemSearchEntity, SearchAdapter.ViewHolder> {

    private String typeSearch = SearchFragment.TYPE_SEARCH_OFFLINE;

    public interface SearchAdapterListener extends IRecyclerViewOnItemClickListener<ItemSearchEntity> {

    }

    public SearchAdapter(Context context, SearchAdapterListener listener) {
        super(context, listener);
    }

    public SearchAdapter(Context context, String typeSearch, IRecyclerViewOnItemClickListener listener) {
        super(context, listener);
        this.typeSearch = typeSearch;
    }

    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_search, parent, false));
    }

    @Override
    public void onBindViewHolderAdapter(ViewHolder holder, int position) {
        holder.onBindData(holder, position);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.item_search_image)
        ImageView mImage;
        @BindView(R.id.item_search_title)
        TextViewRoboto mTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindData(ViewHolder holder, int position) {
            ItemSearchEntity mItem = mDatas.get(getAdapterPosition());

            // setImage
            if (!TextUtils.isEmpty(mItem.mImage)) {
                ImageUtils.loadRoundImage(mContext, "http://zmp3-photo-td.zadn.vn/thumb/600_600/" + mItem.mImage, holder.mImage);
            } else {
                if (typeSearch.equals(SearchFragment.TYPE_SEARCH_OFFLINE)) {
                    holder.mImage.setImageResource(R.drawable.ic_offline_song);
                } else if (typeSearch.equals(SearchFragment.TYPE_SEARCH_ONLINE)) {
                    holder.mImage.setImageResource(R.drawable.ic_offline_album);
                }
            }
            // setTitle
            holder.mTitle.setText(mItem.mTitle);
        }


    }
}

