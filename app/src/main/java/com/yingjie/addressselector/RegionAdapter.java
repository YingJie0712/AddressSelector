package com.yingjie.addressselector;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen.yingjie on 2019/3/23
 */
public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.ViewHolder> {

    public static final int DATA_PROVINCE = 1;// 选择省
    public static final int DATA_CITY = 2;// 选择市
    public static final int DATA_AREA = 3;// 选择县/区

    private List<RegionBean> provinceDatas; // 总（省)数据
    private Context mContext;
    // 显示数据类型
    private int dataType;
    // 选中的省item的position
    private int positionProvince;
    // 选中的市item的position
    private int positionCity;
    // 选中的县/区item的position
    private int positionArea;
    // 记录选择状态
    private List<Boolean> isCheckedP;
    private List<Boolean> isCheckedC;
    private List<Boolean> isCheckedA;

    private String checkedProvince;
    private String checkedCity;
    private String checkedArea;

    private OnItemCheckedListener onItemCheckedListener;

    public RegionAdapter(Context context) {
        mContext = context;
        isCheckedP = new ArrayList<>();
        isCheckedC = new ArrayList<>();
        isCheckedA = new ArrayList<>();
    }

    /**
     * 刷新
     *
     * @param provinceDatas 数据集
     * @param dataType      应该选择(显示)    省：DATA_PROVINCE 市：DATA_CITY 县/区：DATA_AREA
     * @param checkedProvince      已选择省
     * @param checkedCity          已选择市
     * @param checkedArea          已选择县/区
     */
    public void refreshData(List<RegionBean> provinceDatas, int dataType, String checkedProvince, String checkedCity, String checkedArea) {
        this.provinceDatas = provinceDatas;
        this.dataType = dataType;
        this.checkedProvince = checkedProvince;
        this.positionProvince = getProvincePisition(checkedProvince);
        this.positionCity = getCityPosition(checkedProvince, checkedCity);
        this.positionArea = getAreaPosition(checkedProvince, checkedCity, checkedArea);
        // 初始化选中状态false
        if (dataType == DATA_PROVINCE) {
            isCheckedP.clear();
            if (provinceDatas == null) {
                return;
            }
            for (int i = 0; i < provinceDatas.size(); i++) {
                isCheckedP.add(false);
            }
            if (positionProvince != -1) {
                isCheckedP.set(positionProvince, true);
            }
        } else if (dataType == DATA_CITY) {
            isCheckedC.clear();
            if (provinceDatas == null ||
                    provinceDatas.get(positionProvince) == null ||
                    provinceDatas.get(positionProvince).citys == null) {
                return;
            }
            for (int i = 0; i < provinceDatas.get(positionProvince).citys.size(); i++) {
                isCheckedC.add(false);
            }
            if (positionCity != -1) {
                isCheckedC.set(positionCity, true);
            }
        } else if (dataType == DATA_AREA){
            isCheckedA.clear();
            if (provinceDatas == null ||
                    provinceDatas.get(positionProvince) == null ||
                    provinceDatas.get(positionProvince).citys == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity) == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity).areas == null) {
                return;
            }
            for (int i = 0; i < provinceDatas.get(positionProvince).citys.get(positionCity).areas.size(); i++) {
                isCheckedA.add(false);
            }
            if (positionArea != -1) {
                isCheckedA.set(positionArea, true);// 已选中
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_region_recycleview, viewGroup, false);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        List<Boolean> isChecked = new ArrayList<>();

        // 显示数据，把当前position设置为tag
        if (dataType == DATA_PROVINCE) {
            isChecked = isCheckedP;
            if (provinceDatas == null ||
                    provinceDatas.get(position) == null) {
                return;
            }
            holder.tvName.setText(provinceDatas.get(position).provinceName);
        } else if (dataType == DATA_CITY) {
            isChecked = isCheckedC;
            if (provinceDatas == null ||
                    provinceDatas.get(positionProvince) == null ||
                    provinceDatas.get(positionProvince).citys == null ||
                    provinceDatas.get(positionProvince).citys.get(position) == null) {
                return;
            }
            holder.tvName.setText(provinceDatas.get(positionProvince).citys.get(position).cityName);
        } else {
            isChecked = isCheckedA;
            if (provinceDatas == null ||
                    provinceDatas.get(positionProvince) == null ||
                    provinceDatas.get(positionProvince).citys == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity) == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity).areas == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity).areas.get(position) == null) {
                return;
            }
            holder.tvName.setText(provinceDatas.get(positionProvince).citys.get(positionCity).areas.get(position).areaName);
        }
        // 根据选中状态更新UI
        if (isChecked.get(position)) {
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.ff5000));
            holder.tvSelected.setVisibility(View.VISIBLE);
        } else {
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.v666666));
            holder.tvSelected.setVisibility(View.GONE);
        }
        if (onItemCheckedListener != null) {
            final List<Boolean> finalIsChecked = isChecked;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1、所有值重置为false
                    for (int i = 0; i < finalIsChecked.size(); i++) {
                        finalIsChecked.set(i, false);
                    }
                    // 2、当前点击的设置为true
                    finalIsChecked.set(position, true);
                    // 3、更新
                    notifyDataSetChanged();

                    if (dataType == DATA_PROVINCE) {
                        checkedProvince = holder.tvName.getText().toString();
                    } else if (dataType == DATA_CITY) {
                        // 这里不能只给checkedCity赋值，checkedProvince容易为空，在refresh中赋值。
                        checkedCity = holder.tvName.getText().toString();
                    } else {
                        checkedArea = holder.tvName.getText().toString();
                    }
                    onItemCheckedListener.onItemChecked(dataType, checkedProvince, checkedCity, checkedArea);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (dataType == DATA_PROVINCE) {
            if (provinceDatas != null) {
                size = provinceDatas.size();
            }
        } else if (dataType == DATA_CITY) {
            if (provinceDatas != null &&
                    provinceDatas.get(positionProvince) != null &&
                    provinceDatas.get(positionProvince).citys != null) {
                size = provinceDatas.get(positionProvince).citys.size();
            }
        } else { // dataType == DATA_AREA 选择县/区前和后都展示同样的数据
            if (provinceDatas != null &&
                    provinceDatas.get(positionProvince) != null &&
                    provinceDatas.get(positionProvince).citys != null &&
                    provinceDatas.get(positionProvince).citys.get(positionCity) != null &&
                    provinceDatas.get(positionProvince).citys.get(positionCity).areas != null) {
                size = provinceDatas.get(positionProvince).citys.get(positionCity).areas.size();
            }
        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSelected = itemView.findViewById(R.id.tvSelected);
        }
    }

    /**
     * 获取已选省position
     *
     * @param province
     * @return
     */
    public int getProvincePisition(String province) {
        if (provinceDatas != null && !TextUtils.isEmpty(province)) {
            for (int p=0; p<provinceDatas.size(); p++) {
                if (provinceDatas.get(p).provinceName.equals(province)) {
                    return p;
                }
            }
        }
        return -1;
    }

    /**
     * 获取city的position
     *
     * @param province
     * @param city
     * @return
     */
    public int getCityPosition(String province, String city) {
        if (!TextUtils.isEmpty(province) && !TextUtils.isEmpty(city)) {
            if (provinceDatas != null) {
                for (int p=0; p<provinceDatas.size(); p++) {
                    if (provinceDatas.get(p).provinceName.equals(province)) {// 找到province的position
                        if (provinceDatas.get(p) != null && provinceDatas.get(p).citys != null) {
                            for (int c=0; c<provinceDatas.get(p).citys.size(); c++) {
                                if (provinceDatas.get(p).citys.get(c).cityName.equals(city)) {
                                    return c;
                                }
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    public int getAreaPosition(String province, String city, String area) {
        if (!TextUtils.isEmpty(province) && !TextUtils.isEmpty(city)) {
            if (provinceDatas != null) {
                for (int p=0; p<provinceDatas.size(); p++) {
                    if (provinceDatas.get(p).provinceName.equals(province)) {// 找到province的position
                        if (provinceDatas.get(p) != null && provinceDatas.get(p).citys != null) {
                            for (int c=0; c<provinceDatas.get(p).citys.size(); c++) {
                                if (provinceDatas.get(p).citys.get(c).cityName.equals(city)) {
                                    if (provinceDatas.get(p).citys.get(c) != null && provinceDatas.get(p).citys.get(c).areas != null) {
                                        for (int a=0; a<provinceDatas.get(p).citys.get(c).areas.size(); a++) {
                                            if (provinceDatas.get(p).citys.get(c).areas.get(a).areaName.equals(area)) {
                                                return a;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    public void setOnItemCheckedListener(OnItemCheckedListener onItemCheckedListener) {
        this.onItemCheckedListener = onItemCheckedListener;
    }

    public interface OnItemCheckedListener {
        void onItemChecked(int lastDataType, String checkedProvince, String checkedCity, String checkedArea);
    }
}
