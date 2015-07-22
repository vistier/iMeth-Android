package cn.imeth.android.lang.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/7/21.
 */
public abstract class ArrayAdapter<Entity> extends BaseAdapter {

    /** 数组数据 */
    private List<Entity> values = new ArrayList<>();

    @Override
    public Entity getItem(int position) {
        return values.get(position);
    }

    public List<Entity> getItems() {
        return values;
    }

    @Override
    public int getCount() {
        return values.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
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

    public void add(Entity item) {
        values.add(item);

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

}
