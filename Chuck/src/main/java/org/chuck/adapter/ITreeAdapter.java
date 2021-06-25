package org.chuck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.chuck.R;
import org.chuck.common.ViewHolder;
import org.chuck.model.ITreeNode;

import java.util.ArrayList;
import java.util.List;

public abstract class ITreeAdapter<T extends ITreeNode> extends BaseAdapter {

	private LayoutInflater inflater;
	private List<T> allNodes=new ArrayList<>();
	private List<T> showNodes=new ArrayList<>();
	private List<T> rootNodes;

	private Context context;
	public ITreeAdapter(Context context, List<T> rootNodes) {
		this.context=context;
		this.inflater= LayoutInflater.from(context);
		this.rootNodes=rootNodes;
		showNodes.addAll(rootNodes);
	}
	
	
	@Override
	public int getCount() {		
		return showNodes==null?0:showNodes.size();
	}

	@Override
	public T getItem(int position) {
		return showNodes==null?null:showNodes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=ViewHolder.getViewHolder(inflater, convertView, parent, R.layout.item_tree);
		convertView=viewHolder.getConvertView();
		convert(viewHolder, getItem(position), convertView, position);
		return convertView;
	}
	
	public void convert(final ViewHolder holder, final T item, final View convertView, final int position){
		ImageView expandIv=holder.getView(org.chuck.R.id.iv_expand);
		TextView nodeBodyTv=holder.getView(org.chuck.R.id.tv_nodebody);
		nodeBodyTv.setText(item.getName());
		LinearLayout nodeLl=holder.getView(R.id.ll_treenode);
		LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) expandIv.getLayoutParams();
		layoutParams.leftMargin=item.getLevel()*27+25;
		expandIv.setLayoutParams(layoutParams);
		expandIv.setBackgroundResource(item.isExpand() ? R.drawable.expand_on2 : R.drawable.expand_off1);
		expandIv.setVisibility(item.isHaveChildren() ? View.VISIBLE : View.INVISIBLE);
		nodeBodyTv.setCompoundDrawablesWithIntrinsicBounds(item.isHaveChildren() ? (item.isExpand() ? context.getResources().getDrawable(R.drawable.eeee1) : context.getResources().getDrawable(R.drawable.cccc1))
				: context.getResources().getDrawable(R.drawable.dddd1), null, null, null);
		expandIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				actionOnExpandClick(holder, item, convertView, position, item.isExpand());
				if (!item.isExpand()) {
					removeChildren(item);
				} else {
					int i = position;
					addChildren(item, i);
				}
				notifyDataSetChanged();
			}
		});
		nodeBodyTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				actionOnNodeBodyClick(holder, item, convertView, position);
			}
		});
	}

	public void removeChildren(T t){
		List<T> rmChildren=new ArrayList<>();
		removeChildren(t,rmChildren);
		showNodes.removeAll(rmChildren);
	}

	public void removeChildren(T t,List<T> rmChildren){
		String code=t.getCode();
		for(T node:showNodes){
			if(code.equals(node.getParentCode())){
				rmChildren.add(node);
				if(node.isHaveChildren()&& showNodes.contains(node)){
					removeChildren(node,rmChildren);
				}
			}
		}
	}


	public int addChildren(T t,int position){
		String code=t.getCode();
		for (T node:allNodes) {
			if (code.equals(node.getParentCode())) {
				showNodes.add((++position), node);
				if(node.isExpand()){
					position=addChildren(node,position);
				}
			}
		}
		return position;
	}
	public void addTreeNodes(List<T> treeNodes){
		if(treeNodes!=null){
			allNodes.addAll(treeNodes);
		}
	}

	public abstract void actionOnExpandClick(ViewHolder holder, T item, View convertView, int position, boolean isExpand);
	public abstract void actionOnNodeBodyClick(ViewHolder holder, T item, View convertView, int position);
}
