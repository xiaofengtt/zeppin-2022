/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zixueku.fragment;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.adapter.SubjectCardItemAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Grade;
import com.zixueku.entity.ParserType;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.util.App;
import com.zixueku.util.NetThreadUtil;

public class SuperSubjectCardFragment extends Fragment {

	private SubjectCardItemAdapter subjectCardAdapter;

	private List<Grade> grades;
	private int position;

	private User user;

	public static SuperSubjectCardFragment newInstance(List<Grade> grades, int position) {
		SuperSubjectCardFragment f = new SuperSubjectCardFragment(grades, position);
		return f;
	}

	public SuperSubjectCardFragment(List<Grade> grades, int position) {
		super();
		this.grades = grades;
		this.position = position;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.user = ((App) getActivity().getApplication()).getUserInfo();
		View contactsLayout = inflater.inflate(R.layout.subject, container, false);
		ListView listView = (ListView) contactsLayout.findViewById(R.id.subject_list_view);
		subjectCardAdapter = new SubjectCardItemAdapter(this.getActivity(), grades.get(position).getSubjects());
		listView.setAdapter(subjectCardAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int pos, long id) {
				final Boolean isChecked = grades.get(position).getSubjects().get(pos).isSelected();
				grades.get(position).getSubjects().get(pos).setSelected(!isChecked);
				// delete
				HashMap<String, Object> requestData = new HashMap<String, Object>();
				requestData.put("user.id", user.getUserId());
				requestData.put("subject.id", grades.get(position).getSubjects().get(pos).getId());
				int operation = -1;

				if (isChecked) {
					operation = R.string.UserSubjectDelete;
				} else {
					operation = R.string.UserSubjectAdd;
				}

				Request request = new Request(operation, requestData, SuperSubjectCardFragment.this.getActivity(), ParserType.USER_DEFINED,
						ActionResult.class);
				
				NetThreadUtil.sendDataToServerNoProgressDialog(request, new ServerDataCallback<ActionResult<?>>() {

					@Override
					public void processData(ActionResult<?> paramObject, boolean paramBoolean) {
						// TODO Auto-generated method stub
						((CheckBox) (view.findViewById(R.id.subject_check_box_item))).setChecked(!isChecked);
					}
				});

			}
		});

		return contactsLayout;
	}

}