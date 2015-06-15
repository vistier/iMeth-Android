package cn.imeth.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 使用建议:
 * <h3>.对于数据的处理,建议直接交给Adapter处理</h3>
 * <li>@see #reset(Collection) 重置数组数据,常用于刷新数据时的重置</li>
 * <li>@see #reset(Object[]) </li>
 * <li>@see #addAll(Collection) 在数组后面添加新数据,常用于加载更多数据添加</li>
 * <li>@see #addAll(Object[]) </li>
 * <li>@see #insert(Object, int) 在数组特定位置上添加数量</li>
 * <li>@see #remove(Object) 移除数据</li>
 * <li>@see #clear()  清空数组数据</li>
 * <li>@see #getItem(int)  获得某个位置的数据,常用于点击事件时获得对象</li>
 *
 * <h3>2.建议子类处理构造方法的布局控制 </h3>
 * @see #viewRes
 *
 * <h3>3.每个Adapter有独自的ViewHolder</h3>
 *
 * <h3>4.关于Item控件事件</h3>
 * 如果Item控件需要处理事件,建议在Adapter直接处理事件,再用自定义的事件返回给Adapter创建者
 *
 * <h3>5.如果Adapter需要处理item view type</h3>
 * 那ViewHolder需要使用者自己管理类型,详情可参考
 * @see #getView(int, View, ViewGroup)
 * ViewHolder是记录在view中的tag,
 * 对于ViewHolder的实例需要使用自己来处理.
 *
 * Created by 戴文龙(daiwenlong@imeth.cn) on 2014/12/17.
 */
public abstract class ViewHolderArrayAdapter<Holder extends ViewHolderArrayAdapter.ViewHolder, Entity> extends BaseAdapter {

    /** 数组数据 */
    private List<Entity> values = new ArrayList<>();
    /** item布局 */
    private int viewRes;

    private Context context;
    LayoutInflater inflater;

    public ViewHolderArrayAdapter(Context context, int viewRes) {
        this.context = context;
        this.viewRes = viewRes;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            convertView = inflateView();
            holder = initViewHolder(convertView, position);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.setPosition(position);
        fillViewHolder(holder, getItem(position), position);

        return convertView;
    }

    protected View inflateView() {
        return inflater.inflate(this.viewRes, null);
    }

    /**
     * 初始化ViewHolder中的控件
     */
    protected abstract Holder initViewHolder(View view, int position);

    /**
     * 填充ViewHolder中控件的数据
     */
    protected abstract void fillViewHolder(Holder holder, Entity entity, int position);

    @Override
    public Entity getItem(int position) {
        return values.get(position);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 重置数量
     *
     * @param collection
     */
    public void reset(Collection<? extends Entity> collection) {
        synchronized (ViewHolderArrayAdapter.class) {
            values.clear();
            values.addAll(collection);
        }

        notifyDataSetChanged();
    }

    /**
     * 重置数量
     *
     * @param items
     */
    public void reset(Entity... items) {
        synchronized (ViewHolderArrayAdapter.class) {
            values.clear();
            Collections.addAll(values, items);
        }

        notifyDataSetChanged();
    }


    /**
     * 在数组末尾添加数组数据
     *
     * @param collection
     */
    public void addAll(Collection<? extends Entity> collection) {
        synchronized (ViewHolderArrayAdapter.class) {
            values.addAll(collection);
        }

        notifyDataSetChanged();
    }

    /**
     * 在数组末尾添加数组数据
     *
     * @param items
     */
    public void addAll(Entity... items) {
        synchronized (ViewHolderArrayAdapter.class) {
            Collections.addAll(values, items);
        }

        notifyDataSetChanged();
    }

    /**
     * 插入数量到数组index位置
     *
     * @param object
     * @param index
     */
    public void insert(Entity object, int index) {
        synchronized (ViewHolderArrayAdapter.class) {
            values.add(index, object);
        }

        notifyDataSetChanged();
    }

    /**
     * 移除数组中的对象
     *
     * @param object The object to remove.
     */
    public void remove(Entity object) {
        synchronized (ViewHolderArrayAdapter.class) {
            values.remove(object);
        }

        notifyDataSetChanged();
    }

    /**
     * 清空数组中所有数据
     */
    public void clear() {
        synchronized (ViewHolderArrayAdapter.class) {
            values.clear();
        }

        notifyDataSetChanged();
    }

    public void sort(Comparator<? super Entity> comparator) {
        synchronized (ViewHolderArrayAdapter.class) {
            Collections.sort(values, comparator);
        }

        notifyDataSetChanged();
    }

    /**
     * Adapter Item View 对应控件管理Holder,与view是一一对应关系
     * 注意:不应该将对象放置到holder
     */
    public static class ViewHolder {

        private int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
