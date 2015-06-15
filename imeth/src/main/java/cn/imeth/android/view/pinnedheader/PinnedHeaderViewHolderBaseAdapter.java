/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.imeth.android.view.pinnedheader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 戴文龙 on 2014/12/24.
 */
public abstract class PinnedHeaderViewHolderBaseAdapter<SectionHolder extends PinnedHeaderViewHolderBaseAdapter.SectionViewHolder, ItemHolder extends PinnedHeaderViewHolderBaseAdapter.ViewHolder>  extends SectionedBaseAdapter{

    private LayoutInflater inflater;

    private int sectionViewRes;
    private int itemViewRes;

    protected PinnedHeaderViewHolderBaseAdapter(Context context,int sectionViewRes,int itemViewRes) {
        this.sectionViewRes = sectionViewRes;
        this.itemViewRes = itemViewRes;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        SectionHolder holder;

        if(convertView == null) {
            convertView  = inflateView(sectionViewRes);
            holder = initSectionViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SectionHolder) convertView.getTag();
        }

        holder.setSection(section);
        fillSectionViewHolder(holder,section);

        return convertView;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        ItemHolder holder;

        if(convertView == null) {
            convertView  = inflateView(itemViewRes);
            holder = initViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }

        holder.setSection(section);
        fillViewHolder(holder, section, position);
        return convertView;
    }

    protected View inflateView(int res) {
        return inflater.inflate(res, null);
    }

    @Override
    public long getItemId(int section, int position) {
        return section * getSectionCount() + position;
    }

    protected abstract SectionHolder initSectionViewHolder(View view);
    protected  abstract ItemHolder initViewHolder(View view);

    protected  abstract void fillSectionViewHolder(SectionHolder holder, int section);
    protected  abstract void fillViewHolder(ItemHolder holder, int section, int position);

    public Context getContext() {
        return inflater.getContext();
    }

    public static class ViewHolder {

        private int section;
        private int position;

        public int getSection() {
            return section;
        }

        public void setSection(int section) {
            this.section = section;
        }
        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public static class SectionViewHolder {

        private int section;

        public int getSection() {
            return section;
        }

        public void setSection(int section) {
            this.section = section;
        }
    }

}
