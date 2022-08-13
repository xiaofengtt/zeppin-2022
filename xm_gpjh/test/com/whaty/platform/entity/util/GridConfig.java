package com.whaty.platform.entity.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class GridConfig {

	private MyBaseAction action;

	private String start; // 列表开始的位置

	private String limit; // 每页要显示的条目

	private String sort; // 排序列名

	private String dir; // 排序asc/desc

	private String title; // 表格标题

	private String note; // 页面提示信息(HTML格式)

	private String entityMemberName;

	private List<ColumnConfig> listColumnConfig;

	private String extColumnModel;

	private String extFields;

	private String extUpdateFields;

	private String extSearchVars;

	private String extSearchValues;

	private String extSearchItems;

	private String extAddVars;

	private String extAddItems;

	private String extMenus;

	private String extMenuFunctions;

	private String extMenuScripts;

	private List<MenuConfig> listMenuConfig;

	private String extRenderFunctions;

	private String extRenderColumns;

	private List<RenderConfig> listRenderConfig;

	private boolean isPrepared;

	private boolean canAdd;

	private boolean canBatchAdd;

	private boolean canDelete;

	private boolean canUpdate;

	private boolean canExcelUpdate;

	private String updateName;

	private boolean canSearch;

	private boolean preview;

	private String previewDataIndex;

	private String fieldsFilter;

	private String copyPreSearch;

	private String extPreSearchItems;

	private boolean containFile;

	private boolean containFCKEditor;

	private String extHighlightKeywordFunctions;

	private String excelFiledsFilter;

	private boolean showCheckBox = true;
	
	private boolean canExcelExport = true;//框架下方的 excel导出  
	
	private boolean columnFit = false; //重置按钮，默认不展开

	/**
	 * public方法，代Action存储通用变量
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	// return a default value if not set
	public String getLimit() {
		if (limit == null || limit.equals(""))
			return "20";

		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getStart() {
		if (limit == null || limit.equals(""))
			return "0";

		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEntityMemberName() {
		return entityMemberName;
	}

	public void setEntityMemberName(String entityMemberName) {
		this.entityMemberName = entityMemberName;
	}

	public String getCopyPreSearch() {
		return copyPreSearch;
	}

	public boolean isContainFCKEditor() {
		return containFCKEditor;
	}

	public void setContainFCKEditor(boolean containFCKEditor) {
		this.containFCKEditor = containFCKEditor;
	}

	public boolean isContainFile() {
		return containFile;
	}

	public void setContainFile(boolean containFile) {
		this.containFile = containFile;
	}

	/**
	 * 此方法已废弃，使用setCapability(boolean, boolean, boolean, boolean)代替
	 * 设置grid的add、delte、update能力
	 * 
	 * @param canAdd
	 * @param canDelete
	 * @param canUpdate
	 */
	public void setCapability(boolean canAdd, boolean canDelete,
			boolean canUpdate) {
		this.setCapability(canAdd, canDelete, canUpdate, true);
	}

	/**
	 * 设置grid的add、delte、update能力
	 * 
	 * @param canAdd
	 *            是否有添加功能
	 * @param canDelete
	 *            是否有删除功能
	 * @param canUpdate
	 *            是否有修改功能（双击一条记录）
	 * @param canSearch
	 *            是否可以搜索（标题右边下拉按钮）
	 */
	public void setCapability(boolean canAdd, boolean canDelete,
			boolean canUpdate, boolean canSearch) {
		this.setCapability(canAdd, canDelete, canUpdate, canSearch, false);
	}

	/**
	 * 设置grid的add、delte、update能力
	 * 
	 * @param canAdd
	 *            是否有添加功能
	 * @param canDelete
	 *            是否有删除功能
	 * @param canUpdate
	 *            是否有修改功能（双击一条记录）
	 * @param canSearch
	 *            是否可以搜索（标题右边下拉按钮）
	 * @param canBatchAdd
	 *            是否可以excel导入
	 */
	public void setCapability(boolean canAdd, boolean canDelete,
			boolean canUpdate, boolean canSearch, boolean canBatchAdd) {
		this.canAdd = canAdd;
		this.canDelete = canDelete;
		this.canUpdate = canUpdate;
		this.canSearch = canSearch;
		this.canBatchAdd = canBatchAdd;
	}

	public boolean isCanAdd() {
		return canAdd;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public boolean isCanUpdate() {
		return canUpdate;
	}

	public boolean isCanSearch() {
		return canSearch;
	}

	public String getPreviewDataIndex() {
		return previewDataIndex;
	}

	public boolean isPreview() {
		return preview;
	}

	/**
	 * pre search
	 */
	public String getExtPreSearchVars() {
		return getExtSearchVars().replace("_s_", "search_").replace("});",
				",anchor: '90%'});");
	}

	public String getExtPreSearchItems() {
		return extPreSearchItems;
	}

	public String getPreFieldsFilter() {
		return getFieldsFilter().replace("_s_", "search_");
	}

	/**
	 * 构造方法，初始化list成员
	 */
	public GridConfig(MyBaseAction action) {
		this.action = action;
		listColumnConfig = new ArrayList<ColumnConfig>();
		listMenuConfig = new ArrayList<MenuConfig>();
		listRenderConfig = new ArrayList<RenderConfig>();
	}

	/**
	 * 给Grid添加一个column
	 * 
	 * @param name
	 *            列的自然语言名称
	 * @param dataIndex
	 *            Json字符串索引字段名称
	 * @param search
	 *            是否被搜索
	 * @param add
	 *            是否添加、修改时显示此字段
	 * @param list
	 *            是否显示到列表
	 * @param textFieldParameters
	 *            给TextField设置的其他ExtJS参数，以","结尾。例如，校验3位数字："regex:new
	 *            RegExp(/^\\d{3}$/),"
	 */
	public void addColumn(String name, String dataIndex, boolean search,
			boolean add, boolean list, String textFieldParameters) {
		this.addColumn(name, dataIndex, search, add, list, "TextField", false,
				10000, textFieldParameters);
	}

	/**
	 * 给Grid添加一个column
	 * 
	 * @param name
	 *            列的自然语言名称
	 * @param dataIndex
	 *            Json字符串索引字段名称
	 * @param search
	 *            是否被搜索
	 * @param add
	 *            是否添加、修改时显示此字段
	 * @param list
	 *            是否显示到列表
	 * @param type
	 *            GenderRadio
	 *            数据类型: TextField, Date, TextArea, TextEditor, BasicRadio,
	 * @param allowBlank
	 *            编辑时是否允许为空
	 * @param maxLength
	 *            最大长度
	 */
	public void addColumn(String name, String dataIndex, boolean search,
			boolean add, boolean list, String type, boolean allowBlank,
			int maxLength) {
		this.addColumn(name, dataIndex, search, add, list, type, allowBlank,
				maxLength, null);
	}

	/**
	 * 给Grid添加一个column
	 * 
	 * @param name
	 *            列的自然语言名称
	 * @param dataIndex
	 *            Json字符串索引字段名称
	 * @param search
	 *            是否被搜索
	 */
	public void addColumn(String name, String dataIndex, boolean search) {
		this.addColumn(name, dataIndex, search, true, true, "TextField", false,
				25, null);
	}
	
	/**
	 * 给Grid添加一个column
	 * 
	 * @param name
	 *            列的自然语言名称
	 * @param dataIndex
	 *            Json字符串索引字段名称
	 * @param comboboxType
	 *            下拉菜单类型
	 */
	public void addColumn(String name, String dataIndex, String comboboxType) {
		this.addColumn(name, dataIndex, true, true, true, "TextField", false,
				25, null,comboboxType);
	}
	/**
	 * 给Grid添加一个参与搜索的column
	 * 
	 * @param name
	 * @param dataIndex
	 */
	public void addColumn(String name, String dataIndex) {
		this.addColumn(name, dataIndex, true);
	}

	/**
	 * 给Grid添加一个column (参数太多，所以目前是private方法)  //暂时修改为public 09-11-13 gaoyuan
	 * 
	 * @param name
	 * @param dataIndex
	 * @param search
	 * @param add
	 * @param list
	 * @param type
	 * @param allowBlank
	 * @param maxLength
	 * @param textFieldParameters
	 */
	public void addColumn(String name, String dataIndex, boolean search,
			boolean add, boolean list, String type, boolean allowBlank,
			int maxLength, String textFieldParameters) {
		listColumnConfig.add(new ColumnConfig(name, dataIndex, search, add,
				list, type, allowBlank, maxLength, textFieldParameters));
	}
	
	/**
	 * 给Grid添加一个column 
	 * 
	 * @param name
	 * @param dataIndex
	 * @param search
	 * @param add
	 * @param list
	 * @param type
	 * @param allowBlank
	 * @param maxLength
	 * @param textFieldParameters
	 * @param comboboxType 下拉菜单类型
	 */
	public void addColumn(String name, String dataIndex, boolean search,
			boolean add, boolean list, String type, boolean allowBlank,
			int maxLength, String textFieldParameters,String comboboxType ) {
		listColumnConfig.add(new ColumnConfig(name, dataIndex, search, add,
				list, type, allowBlank, maxLength, textFieldParameters,comboboxType));
	}

	public void addColumn(ColumnConfig column) {
		listColumnConfig.add(column);
	}

	/**
	 * 增加一个在预览行显示的列
	 * 
	 * @param dataIndex
	 */
	public void addPreviewColumn(String dataIndex) {
		this.addColumn(null, dataIndex, false, false, false, null, false, 0,
				null);
		this.preview = true;
		this.previewDataIndex = dataIndex;
	}

	/**
	 * 增加一个按钮
	 * 
	 * @param name
	 *            按钮的名字
	 * @param code
	 *            按钮类型（1为批量更新）
	 */
	public void addMenuFunction(String name, int code) {
		if (code == 1) {
			this.setCanExcelUpdate(true);
			this.setUpdateName(name);
		}
	}

	/**
	 * 给Grid添加一个Menu，设置表格用户选定元素的的某一column为指定value
	 * 
	 * @param name
	 *            Menu的自然语言名称
	 * @param columnDataIndex
	 *            操作的Column数据索引
	 * @param value
	 *            要设置的value
	 */
	// public void addMenuFunction(String name, String columnDataIndex, String
	// value) {
	// listMenuConfig.add(new MenuConfig(name, columnDataIndex, value,
	// "function"));
	// }
	public void addMenuFunction(String name, String... args) {
		
		if (!checkBeforeAddMenu(this.action.getServletPath()
				+ "_abstractUpdateColumn.action"))
			return;

		String columnDataIndex = "";
		String value = "";
		for (int i = 0; i < args.length; i++) {
			if (i == 0) {
				
				columnDataIndex += args[i];
			} else if (i == 1) {
				value += args[i];
			} else if (i % 2 == 0) {
				columnDataIndex += "," + args[i];
			} else {
				value += "," + args[i];
			}
		}
		listMenuConfig.add(new MenuConfig(name, columnDataIndex, value,
				"function"));
	}

	/**
	 * 把选中的id post到一个目标action（的某一方法）
	 * 
	 * @param name
	 *            菜单显示名称
	 * @param targetAction
	 *            目标action路径（或url）
	 * @param forceSingleSelect
	 *            是否限制只能选中一条记录
	 * @param postByAjax
	 *            是否使用ajax post数据 postByAjax：true——使用ajax post数据（当前页面返回结果提示）；
	 *            postByAjax：false——使用form post数据（本页面将跳转到目标action页面）；
	 */
	public void addMenuFunction(String name, String targetAction,
			boolean forceSingleSelect, boolean postByAjax) {
		if (!checkBeforeAddMenu(targetAction))
			return;

		listMenuConfig.add(new MenuConfig(name, targetAction, "id",
				forceSingleSelect, postByAjax, "functionB"));
	}

	/**
	 * 把选中的列的 checkColumn 的值 post到一个目标action（的某一方法）
	 * 
	 * @param name
	 *            菜单显示名称
	 * @param targetAction
	 *            目标action路径（或url）
	 * @param checkColumn
	 *            勾选后所传的列值（默认为id）
	 * @param forceSingleSelect
	 *            是否限制只能选中一条记录
	 * @param postByAjax
	 *            是否使用ajax post数据 postByAjax：true——使用ajax post数据（当前页面返回结果提示）；
	 *            postByAjax：false——使用form post数据（本页面将跳转到目标action页面）；
	 */
	public void addMenuFunction(String name, String targetAction,
			String checkColumn, boolean forceSingleSelect, boolean postByAjax) {
		
		if (!checkBeforeAddMenu(targetAction))
			return;

		listMenuConfig.add(new MenuConfig(name, targetAction, checkColumn,
				forceSingleSelect, postByAjax, "functionB"));
	}

	/**
	 * 给Grid添加一个Menu上的Script功能，直接书写javascript代码
	 * 
	 * @param name
	 *            列标题显示的自然语言名称，例如: "状态"
	 * @param script
	 *            javascript代码，以"{", "}"为起始结束边界 例如:
	 *            "{window.location='/test/somePage.action';}"
	 */
	public void addMenuScript(String name, String script) {
		listMenuConfig.add(new MenuConfig(name, null, script, "script"));
	}

	/**
	 * 给Grid添加一个Column上的Render功能，将值做简单替换转换后输出到列
	 * 
	 * @param name
	 *            列标题显示的自然语言名称，例如: "查看"
	 * @param renderer
	 *            转换规则，支持一个变量 例如: "<a href=xxx_viewDetail.action?id=${value}
	 *            target=_blank>查看详细信息</a>"
	 * @param columnDataIndex
	 *            从addColumn中已经添加的列中获取名为columnDataIndex的列的数据， 作为变量传如render的
	 *            ${value}
	 */
	public void addRenderFunction(String name, String renderer,
			String columnDataIndex) {
		listRenderConfig.add(new RenderConfig(name, renderer, columnDataIndex,
				"function"));
	}

	/**
	 * 非常难用的方法！ 请注意引号的多层转换 给Grid添加一个Render功能，可以将当前行的记录做某种运算后输出
	 * 
	 * @param name
	 *            列标题显示的自然语言名称，例如: "查看"
	 * @param renderer
	 *            转换规则。可以使用 record.data['columnDataIndex'] 来引用列名为
	 *            columnDataIndex 列的值
	 */
	public void addRenderFunction(String name, String renderer) {
		this.addRenderFunction(name, renderer, "");
	}

	/**
	 * 给Grid添加一个Column上的Script功能，直接书写javascript代码 将变量运算后return一个值，输出到列
	 * 
	 * @param name
	 *            列标题显示的自然语言名称，例如: "状态"
	 * @param script
	 *            javascript代码，以"{", "}"为起始结束边界 例如: "{if (${value}=='0') return
	 *            '未提交'; if (${value}=='1') return '已提交'; if (${value}=='2')
	 *            return '已审核'; if (${value}=='3') return '审核未通过';}"
	 * @param columnDataIndex
	 */
	public void addRenderScript(String name, String script,
			String columnDataIndex) {
		listRenderConfig.add(new RenderConfig(name, script, columnDataIndex,
				"script"));
	}

	/**
	 * 非常难用的方法！ 暂不开放为public 给Grid添加一个Script功能，直接书写javascript代码
	 * 
	 * @param name
	 *            列标题显示的自然语言名称，例如: "状态"
	 * @param script
	 *            javascript代码，以"{", "}"为起始结束边界。可以使用
	 *            record.data['columnDataIndex'] 来引用列名为 columnDataIndex 列的值
	 */
	private void addRenderScript(String name, String script) {
		this.addRenderScript(name, script, "");
	}

	/**
	 * getExt... 方法，供页面struts标签输出调用
	 * 
	 * @return
	 */
	public String getExtFields() {

		this.prepare();

		return extFields;
	}

	public String getExtUpdateFields() {

		this.prepare();

		return extUpdateFields;
	}

	public String getExtColumnModel() {

		this.prepare();

		return extColumnModel;
	}

	public String getExtSearchItems() {

		this.prepare();

		return extSearchItems;
	}

	public String getExtSearchVars() {

		this.prepare();

		return extSearchVars;
	}

	public String getExtSearchVarsWithValue() {
		this.prepare();
		String extSearchVarsWithValue = extSearchVars.replaceAll(
				"name:'(.*?)',\\s*id", "name:'$1',value: $1_value,id");
		return extSearchVarsWithValue;
	}

	public String getExtSearchValues() {

		this.prepare();

		return extSearchValues;
	}

	public String getExtAddItems() {

		this.prepare();

		return extAddItems;
	}

	public String getExtAddVars() {

		this.prepare();

		return extAddVars;
	}

	public String getExtMenus() {

		this.prepare();

		return extMenus;
	}

	public String getExtMenuFunctions() {

		this.prepare();

		return extMenuFunctions;
	}

	public String getExtMenuScripts() {

		this.prepare();

		return extMenuScripts;
	}

	public String getExtRenderColumns() {

		this.prepare();

		return extRenderColumns;
	}

	public String getExtRenderFunctions() {

		this.prepare();

		return extRenderFunctions;
	}

	public String getFieldsFilter() {

		this.prepare();

		return fieldsFilter;
	}

	public String getExtHighlightKeywordFunctions() {

		this.prepare();

		return extHighlightKeywordFunctions;
	}

	public List<ColumnConfig> getListColumnConfig() {
		return listColumnConfig;
	}

	public List<MenuConfig> getListMenuConfig() {
		return listMenuConfig;
	}

	/**
	 * prepare()方法 准备给页面层需要的输出元素 页面get...()方法之前需要先调用此方法
	 */
	private void prepare() {

		if (this.isPrepared) {
			return;
		}

		extFields = "";

		extUpdateFields = "";

		extColumnModel = "";

		extSearchVars = "";

		extSearchValues = "";

		extSearchItems = "";

		extPreSearchItems = "";

		extAddVars = "";

		extAddItems = "";

		fieldsFilter = "";

		copyPreSearch = "";

		extMenus = "";

		extMenuFunctions = "";

		extMenuScripts = "";

		extRenderFunctions = "";

		extRenderColumns = "";

		extHighlightKeywordFunctions = "";

		excelFiledsFilter = "";

		/**
		 * 构建Grid，与Column相关元素
		 */
		for (int i = 0; i < listColumnConfig.size(); i++) {

			ColumnConfig column = listColumnConfig.get(i);
			if (i == 0) {
				/**
				 * 第一列
				 */

				extFields += "'" + column.getDataIndex() + "'";

				extUpdateFields += "'bean." + column.getDataIndex() + "'";

				/**
				 * 第一列必须为id，id不显示。张利斌 20080805
				 */
				// extColumnModel += "{id:'" + column.getDataIndex()
				// + "',header:'" + column.getName() + "',dataIndex:'"
				// + column.getDataIndex() + "',width:100},\n";
				extAddItems += column.getDataIndex();

			} else {
				/**
				 * 其他列
				 */

				extFields += ",'" + column.getDataIndex() + "'";

				extUpdateFields += ",'bean." + column.getDataIndex() + "'";

				if (column.isList()) {
					if (!column.getDataIndex().endsWith(".id")) {
						extColumnModel += "{header:'" + column.getName()
								+ "',dataIndex:'" + column.getDataIndex()
								+ "',width:100";

						if (this.canSearch && column.isSearch()) {
							extColumnModel += ", renderer: highlightKeyword_"
									+ column.getDataIndex_();
						}

						extColumnModel += "},\n";
					}
				}

				if (column.isAdd()) {
					if (column.getDataIndex().indexOf(".") < 0) {
						extAddItems += "," + column.getDataIndex_();
						if (column.getType().equals("File"))
							extAddItems += "," + column.getDataIndex_()
									+ "upload";
					} else {
						String bean = column.getDataIndex();
						if (bean.endsWith(".name")) {
							bean = bean.substring(0, bean.length() - 5);
							if (bean.indexOf(".") < 0) {
								extAddItems += "," + column.getDataIndex_();
								if (column.getType().equals("File"))
									extAddItems += "," + column.getDataIndex_()
											+ "upload";
							}
						}
					}
				}
			}

			/**
			 * 对所有列
			 */
			if (this.canSearch && column.isSearch()) {

				String searchBean = column.getDataIndex();

				// 定义变量保存输入条件的值
				extSearchValues += "var _s_" + column.getDataIndex_()
						+ "_value = document.getElementById('_s_"
						+ column.getDataIndex_() + "').value; \n";

				if (column.getType() != null
						&& column.getType().equals("TextField")
						&& searchBean.endsWith(".name")
						&& !searchBean.toLowerCase().startsWith("combobox_")) {
					/**
					 * 如果是.name结尾，创建一个允许用户输入的comboBox
					 */

					/**
					 * 取出最内层的bean名称
					 */
					searchBean = searchBean.substring(0,
							searchBean.length() - 5);

					while (searchBean.indexOf(".") > 0) {
						searchBean = searchBean.substring(searchBean
								.indexOf(".") + 1);
					}

					// String url = null;
					List comboList = null;
					if (column.getComboSQL() != null) {
						// url = "/test/myList.action?sql=" +
						// column.getComboSQL().replace("'", "\\'");
						comboList = this.action.getMyListService().queryBySQL(
								column.getComboSQL());
					} else {
						
						searchBean = searchBean.substring(0, 1).toUpperCase()
								+ searchBean.substring(1);
						System.out.println(searchBean);
						System.out.println(BasicData.cities.size());
						if("PeProvince".equals(searchBean)){
							comboList = BasicData.peProvinces;
						}else if("City".equals(searchBean)){
							comboList = BasicData.cities;
						}else if("County".equals(searchBean)){
							comboList = BasicData.counties;
						}else if("PeSubject".equals(searchBean)){
							comboList = BasicData.peSubjects;
						}else if("Folk".equals(searchBean)){
							comboList = BasicData.folks;
						}else if("UnitAttribute".equals(searchBean)){
							comboList = BasicData.unitAttributes;
						}else if("Education".equals(searchBean)){
							comboList = BasicData.educations;
						}else if("JobTitle".equals(searchBean)){
							comboList = BasicData.cities;
						}else if("MainTeachingSubject".equals(searchBean)){
							comboList = BasicData.mainTeachingSubject;
						}else if("MainTeachingGrade".equals(searchBean)){
							comboList = BasicData.mainTeachingGrade;
						}else if("UnitType".equals(searchBean)){
							comboList = BasicData.unitTypes;
						}else if("PeUnitByFkUnitFrom".equals(searchBean)){
							comboList = BasicData.units;
						}else{
							// url = "/test/myList.action?bean=" + searchBean;
							comboList = this.action.getMyListService()
									.getIdNameList(searchBean);
						}
						
					}
					extSearchVars += "var _s_"
							+ column.getDataIndex_()
							+ " = new Ext.form."+column.getComboboxType()+"({"
							+ // 不同于addVars

							/**
							 * remote的combobox存在问题，已改为用本地实现
							 */
							// " store: new Ext.data.Store({" +
							// " proxy: new Ext.data.HttpProxy({url: '" + url +
							// "'})," +
							// " reader: new Ext.data.JsonReader({root:
							// 'models',totalProperty: 'totalCount',id:
							// 'id',fields: ['id','name' ]})," +
							// " forceSelection:true," +
							// " remoteSort: true" +
							// " })," +
							"	store: new Ext.data.SimpleStore({"
							+ "        fields: ['id', 'name'],"
							+ "        data : [";

					for (int j = 0; j < comboList.size(); j++) {
//System.out.print(",j is "+j);
						int jj=j;
						Object[] s = (Object[]) comboList.get(jj);
						if (j == 0)
							extSearchVars += "        	['" + s[0].toString()
									+ "', '" + s[1].toString() + "']";
						else
							extSearchVars += ",        	['" + s[0].toString()
									+ "', '" + s[1].toString() + "']";
					}

					extSearchVars += "        ]" + "   })," +

					"	valueField: 'id'," + "	displayField:'name',"
							+ "	selectOnFocus:true," + "	allowBlank: true,"
							+ // 不同于addVars
							"	typeAhead:false,"
							+ // 不同于addVars
							"	fieldLabel: '" + column.getName() + "',"
							+ // 不同于addVars
							"	name:'_s_" + column.getDataIndex_() + "',"
							+ // 不同于addVars
							"	id:'_s_" + column.getDataIndex_() + "',"
							+ // 不同于addVars
							"	triggerAction: 'all'," + "	editable: true,"
							+ // 不同于addVars
							"	mode:'local'," + "	emptyText:'',"
							+ "	blankText:''" + "});\n";
				} else if (searchBean.toLowerCase().startsWith("combobox_")) {

					/**
					 * 如果是以combobox_开头，形如(ComboBox_PeSite.sitename)，创建一个允许用户输入的comboBox
					 */
					List comboList = null;
					if (column.getComboSQL() != null) {
						// 自定SQL优先
						comboList = this.action.getMyListService().queryBySQL(
								column.getComboSQL());
					} else {
						try {
							// 取出bean
							searchBean = searchBean.substring(9, searchBean
									.indexOf("."));

							searchBean = searchBean.substring(0, 1)
									.toUpperCase()
									+ searchBean.substring(1);
						} catch (Exception e) {
						}
						comboList = this.action.getMyListService()
								.getIdNameList(searchBean);

					}

					extSearchVars += "var _s_"
							+ column.getDataIndex_()
							+ " = new Ext.form."+column.getComboboxType()+"({"
							+ // 不同于addVars
							"	store: new Ext.data.SimpleStore({"
							+ "        fields: ['id', 'name'],"
							+ "        data : [";

					for (int j = 0; j < comboList.size(); j++) {
						Object[] s = (Object[]) comboList.get(j);
						if (j == 0)
							extSearchVars += "        	['" + s[0].toString()
									+ "', '" + s[1].toString() + "']";
						else
							extSearchVars += ",        	['" + s[0].toString()
									+ "', '" + s[1].toString() + "']";
					}

					extSearchVars += "        ]" + "   })," +

					"	valueField: 'id'," + "	displayField:'name',"
							+ "	selectOnFocus:true," + "	allowBlank: true,"
							+ "	typeAhead:false," + "	fieldLabel: '"
							+ column.getName() + "',"
							+ "	name:'_s_"
							+ column.getDataIndex_()
							+ "',"
							+ // ComboBox_PeSite.sitename 获取sitename作为其name值
							"	id:'_s_" + column.getDataIndex_() + "',"
							+ "	triggerAction: 'all'," + "	editable: true,"
							+ "	mode:'local'," + "	emptyText:'',"
							+ "	blankText:''" + "});\n";
				} else if (searchBean.endsWith("Date")
						|| column.getType() != null
						&& column.getType().equals("Date")) {
					/**
					 * 创建日期框
					 */
					extSearchVars += "var _s_" + column.getDataIndex_()
							+ " = new Ext.form.DateField({" + "fieldLabel: '"
							+ column.getName() + "'," + " format: 'Y-m-d', "
							+ "readOnly: false," + " width: 150,"
							+ " anchor: '100%', " + "name:'_s_"
							+ column.getDataIndex() + "'," + "id: '_s_"
							+ column.getDataIndex_() + "'" + "});\n ";

				} else if (searchBean.endsWith("Datetime")
						|| column.getType() != null
						&& column.getType().equals("Datetime")) {
					/**
					 * 创建日期时间框
					 */
					extSearchVars += "var _s_" + column.getDataIndex_()
							+ " = new Ext.form.DateField({" + "fieldLabel: '"
							+ column.getName() + "',"
							+ " format: 'Y-m-d H:i:s', "
							+ "  menu:new DatetimeMenu(), "
							+ "readOnly: false," + " width: 150,"
							+ " anchor: '100%', " + "name:'_s_"
							+ column.getDataIndex() + "'," + "id: '_s_"
							+ column.getDataIndex_() + "'" + "});\n ";
				} else {
					/**
					 * 否则，创建一个普通文本框
					 */
					extSearchVars += "var _s_" + column.getDataIndex_()
							+ " = new Ext.form.TextField({"
							+ "	        fieldLabel: '" + column.getName()
							+ "'," + "	        name:'_s_"
							+ column.getDataIndex_() + "',"
							+ "	        id: '_s_" + column.getDataIndex_()
							+ "'	    " + "});\n";
				}

				extSearchItems += "{"
						+ "                columnWidth:280/panelW,"
						+ "                layout: 'form',"
						+ "                items: [_s_"
						+ column.getDataIndex_() + "]            " + "},\n";

				extPreSearchItems += "search_" + column.getDataIndex_() + ",\n";

				fieldsFilter += ", '" + "search__" + column.getDataIndex()
						+ "':Ext.get('_s_" + column.getDataIndex_()
						+ "').dom.value";

				excelFiledsFilter += " <input type='hidden' name='search__"
						+ column.getDataIndex() + "' value='\" + Ext.get('_s_"
						+ column.getDataIndex_() + "').dom.value +\"' />";

				copyPreSearch += "Ext.get('_s_" + column.getDataIndex_()
						+ "').dom.value = Ext.get('search_"
						+ column.getDataIndex_() + "').dom.value;\n";

				extHighlightKeywordFunctions += "function highlightKeyword_"
						+ column.getDataIndex_()
						+ "(p_value, metadata, record) {"
						+ "return p_value.replace(" + "Ext.get('_s_"
						+ column.getDataIndex_() + "').dom.value,"
						+ "'<font color=#FF0000>'+Ext.get('_s_"
						+ column.getDataIndex_() + "').dom.value+'</font>');"
						+ "}";
			}

			String bean = column.getDataIndex();

			if (bean.indexOf(".") < 0) {

				/*
				 * if 主bean的字段，直接构造输入域
				 */
				if ((column.getType() != null && column.getType()
						.equalsIgnoreCase("Date"))
						|| bean.endsWith("Date")) {
					/*
					 * 如果显式声明列Column为Date类型，或以Date结尾，认为是日期类型
					 */
					extAddVars += "var " + column.getDataIndex_()
							+ " = new Ext.form.DateField({" + "fieldLabel: '"
							+ column.getName()
							+ (column.isAllowBlank() ? "" : "*")
							+ "', allowBlank: " + column.isAllowBlank() + ","
							+ " format: 'Y-m-d', " + "readOnly: false,"
							+ "name: '" + this.getEntityMemberName() + "."
							+ column.getDataIndex() + "', " + "anchor: '60%' "
							+ "});\n";

				} else if ((column.getType() != null && column.getType()
						.equalsIgnoreCase("Datetime"))
						|| bean.endsWith("Datetime")) {
					/*
					 * 如果显式声明列Column为Datetime类型，认为是日期时间类型
					 */
					extAddVars += "var " + column.getDataIndex_()
							+ " = new Ext.form.DateField({"
							+ "fieldLabel: '"
							+ column.getName()
							+ (column.isAllowBlank() ? "" : "*")
							+ "', allowBlank: "
							+ column.isAllowBlank()
							+ ","
							+ " format: 'Y-m-d H:i:s', "
							+ "  menu:new DatetimeMenu(), " // DatetimeMenu
							+ "readOnly: false," + "name: '"
							+ this.getEntityMemberName() + "."
							+ column.getDataIndex() + "', " + "anchor: '60%' "
							+ "});\n";

				} else if ((column.getType() != null && column.getType()
						.equalsIgnoreCase("Textarea"))
						|| bean.endsWith("Note")) {
					/*
					 * 如果显式声明列Column为Textarea类型，或以Note结尾，认为是长文本类型
					 */
					String parameters;

					if (column.getTextFieldParameters() != null) {
						parameters = column.getTextFieldParameters();
					} else {
						parameters = "maxLength:" + column.getMaxLength()
								+ ", ";
					}

					extAddVars += "var " + column.getDataIndex_()
							+ " = new Ext.form.TextArea({" + "fieldLabel: '"
							+ column.getName()
							+ (column.isAllowBlank() ? "" : "*") + "', "
							+ "name: '" + this.getEntityMemberName() + "."
							+ column.getDataIndex() + "', " + "allowBlank:"
							+ column.isAllowBlank() + "," + parameters
							+ "anchor: '90%'" + "});\n";

				} else if ((column.getType() != null && column.getType()
						.equalsIgnoreCase("TextEditor"))) {
					/*
					 * 如果显式声明列Column为TextEditor类型，认为是需要在添加修改时以FCKEditor编辑，弹出框将放大
					 */
					String parameters;

					if (column.getTextFieldParameters() != null) {
						parameters = column.getTextFieldParameters();
					} else {
						parameters = "maxLength:" + column.getMaxLength()
								+ ", ";
					}

					extAddVars += "var " + column.getDataIndex_()
							+ " = new Ext.form.TextArea({" + "fieldLabel: '"
							+ column.getName()
							+ (column.isAllowBlank() ? "" : "*") + "', "
							+ "xtype: 'textarea',hideLabel: true," + "name: '"
							+ this.getEntityMemberName() + "."
							+ column.getDataIndex() + "', " + "id: '"
							+ this.getEntityMemberName() + "."
							+ column.getDataIndex() + "', " + "allowBlank:"
							+ column.isAllowBlank() + "," + parameters
							+ "anchor: '100% -30'" + "});\n";
					this.setContainFCKEditor(true);

				} else if ((column.getType() != null && column.getType()
						.endsWith("Radio"))) {
					/*
					 * 如果显式声明列Column为Radio类型，认为是Radio，BasicRadio为是否选择 value:0/1
					 * GenderRadio为男女选择 value:男/女
					 */
					String parameters;

					if (column.getTextFieldParameters() != null) {
						parameters = column.getTextFieldParameters();
					} else {
						parameters = "maxLength:" + column.getMaxLength()
								+ ", ";
					}

					extAddVars += "var " + column.getDataIndex_()
							+ " = new Ext.Panel({" + "layout: 'table',"
							+ "isFormField: true," + "defaultType: 'radio',"
							+ "fieldLabel: '" + column.getName()
							+ (column.isAllowBlank() ? "" : "*") + "', "
							+ "name: '" + this.getEntityMemberName() + "."
							+ column.getDataIndex() + "', " + "id: '"
							+ this.getEntityMemberName() + "."
							+ column.getDataIndex() + "', " + "allowBlank:"
							+ column.isAllowBlank() + "," + parameters
							+ "anchor: '60%',";
					if (column.getType().equalsIgnoreCase("GenderRadio")) {
						extAddVars += "items: [{" + "name: '"
								+ this.getEntityMemberName() + "."
								+ column.getDataIndex_() + "',"
								+ "boxLabel: '男'," + "inputValue: '男' " + "},{"
								+ "name: '" + this.getEntityMemberName() + "."
								+ column.getDataIndex_() + "',"
								+ "checked:true," + "boxLabel: '女',"
								+ "inputValue: '女' " + "}]" + "});  ";
					} else {
						extAddVars += "items: [{" + "name: '"
								+ this.getEntityMemberName() + "."
								+ column.getDataIndex_() + "',"
								+ "boxLabel: '是'," + "inputValue: '1' " + "},{"
								+ "name: '" + this.getEntityMemberName() + "."
								+ column.getDataIndex_() + "',"
								+ "checked:true," + "boxLabel: '否',"
								+ "inputValue: '0' " + "}]" + "});  ";
					}

				} else if ((column.getType() != null && column.getType()
						.equals("File"))) {

					String parameters = "";

					// 文件类型输入本地路径实际不写入数据库，不应限制输入长度
					if (column.getTextFieldParameters() != null) {
						parameters = column.getTextFieldParameters();
					}

					/**
					 * 去掉不必要代码 张利斌 2009-1-21 文件类型不需要正则表达式校验
					 *  // 如果参数中不含有正则表达式，强制限制不能以空格开头和结尾 if (parameters != null &&
					 * parameters.indexOf("regex") < 0) {
					 * if(bean.equals("name")) { parameters += "regex:new
					 * RegExp(/^([^\\s()]|[^\\s()][^()]*[^\\s()])$/),regexText:'输入格式：名称字段不能以空格开头和结尾、不能包含半角括号',";
					 * }else{ parameters += "regex:new
					 * RegExp(/^(\\S|\\S.*\\S)$/),regexText:'输入格式：不能以空格开头和结尾',"; } }
					 */

					extAddVars += "var " + column.getDataIndex_() + "upload"
							+ " = new Ext.form.TextField({" + "fieldLabel: '"
							+ column.getName()
							+ (column.isAllowBlank() ? "" : "*") + "', "
							+ "name: '_upload', " + "allowBlank:"
							+ column.isAllowBlank() + "," + "inputType:'file',"
							+ parameters + "anchor: '90%'" + "});\n";

					extAddVars += "var " + column.getDataIndex_()
							+ " = new Ext.form.TextField({" + "fieldLabel: '"
							+ column.getName()
							+ (column.isAllowBlank() ? "" : "*") + "', "
							+ "name: '_uploadField', " + "value: '"
							+ column.getDataIndex() + "', " + "allowBlank:"
							+ column.isAllowBlank() + ","
							+ "inputType:'hidden'," + parameters
							+ "anchor: '90%'" + "});\n";

					this.containFile = true;
				} else {
					/*
					 * 否则，按文本类型处理
					 */
					String parameters;

					if (column.getTextFieldParameters() != null) {
						parameters = column.getTextFieldParameters();
					} else {
						parameters = "maxLength:" + column.getMaxLength()
								+ ", ";
					}

					// 如果参数中不含有正则表达式，强制限制不能以空格开头和结尾，name字段不允许包含半角括号
					if (parameters != null && parameters.indexOf("regex") < 0) {
						if (bean.equals("name")) {
							parameters += "regex:new RegExp(/^([^\\s()]|[^\\s()][^()]*[^\\s()])$/),regexText:'输入格式：名称字段不能以空格开头和结尾、不能包含半角括号',";
						} else {
							parameters += "regex:new RegExp(/^(\\S|\\S.*\\S)$/),regexText:'输入格式：不能以空格开头和结尾',";
						}
					}

					extAddVars += "var " + column.getDataIndex_()
							+ " = new Ext.form.TextField({" + "fieldLabel: '"
							+ column.getName()
							+ (column.isAllowBlank() ? "" : "*") + "', "
							+ "name: '" + this.getEntityMemberName() + "."
							+ column.getDataIndex() + "', " + "allowBlank:"
							+ column.isAllowBlank() + "," + parameters
							+ "anchor: '90%'" + "});\n";
				}
			} else {

				/*
				 * else 子bean的字段，只取出name字段，构造为 ComboBox
				 */
				if (bean.endsWith(".name")) {

					bean = bean.substring(0, bean.length() - 5);

					if (bean.indexOf(".") < 0) {

						List comboList = null;
						if (column.getComboSQL() != null) {
							comboList = this.action.getMyListService()
									.queryBySQL(column.getComboSQL());

						} else {
							bean = bean.substring(0, 1).toUpperCase()
									+ bean.substring(1);
							if("PeProvince".equals(bean)){
								comboList = BasicData.peProvinces;
							}else if("City".equals(bean)){
								comboList = BasicData.cities;
							}else if("County".equals(bean)){
								comboList = BasicData.counties;
							}else if("PeSubject".equals(bean)){
								comboList = BasicData.peSubjects;
							}else if("Folk".equals(bean)){
								comboList = BasicData.folks;
							}else if("UnitAttribute".equals(bean)){
								comboList = BasicData.unitAttributes;
							}else if("Education".equals(bean)){
								comboList = BasicData.educations;
							}else if("JobTitle".equals(bean)){
								comboList = BasicData.cities;
							}else if("MainTeachingSubject".equals(bean)){
								comboList = BasicData.mainTeachingSubject;
							}else if("MainTeachingGrade".equals(bean)){
								comboList = BasicData.mainTeachingGrade;
							}else if("UnitType".equals(bean)){
								comboList = BasicData.unitTypes;
							}else if("PeUnitByFkUnitFrom".equals(bean)){
								comboList = BasicData.units;
							}else{
								comboList = this.action.getMyListService()
										.getIdNameList(bean);
							}
							
						}

						extAddVars += "var " + column.getDataIndex_()
								+ " = new Ext.form.ComboBox({"
								+ "	store: new Ext.data.SimpleStore({"
								+ "        fields: ['id', 'name'],"
								+ "        data : [";

						for (int j = 0; j < comboList.size(); j++) {
							Object[] s = (Object[]) comboList.get(j);
							if (j == 0)
								extAddVars += "        	['" + s[0].toString()
										+ "', '" + s[1].toString() + "']";
							else
								extAddVars += ",        ['" + s[0].toString()
										+ "', '" + s[1].toString() + "']";
						}

						extAddVars += "	]})," + "	valueField: 'id',"
								+ "	displayField:'name',"
								+ "	selectOnFocus:true,"
								+ "	forceSelection:true," + // 不同于search
								"	allowBlank:"
								+ column.isAllowBlank()
								+ ","
								+ "	typeAhead:true,"
								+ "	fieldLabel: '"
								+ column.getName()
								+ (column.isAllowBlank() ? "" : "*")
								+ "',"
								+ "	name:'"
								+ this.getEntityMemberName()
								+ "."
								+ column.getDataIndex()
								+ "',"
								+ "	id:'"
								+ this.getEntityMemberName()
								+ "."
								+ column.getDataIndex()
								+ "',"
								+ "	triggerAction: 'all',"
								+ "	editable: true,"
								+ "	mode:'local',"
								+ "	emptyText:'',"
								+ "	anchor: '90%' ,"
								+ "	blankText:''"
								+ "});\n";
					}
				}
				/*
				 * 
				 */
				if (bean.endsWith("_name")) {
					bean = bean.substring(0, bean.length() - 5);

					if (bean.indexOf("_") < 0) {

						List comboList = null;
						if (column.getComboSQL() != null) {
							comboList = this.action.getMyListService()
									.queryBySQL(column.getComboSQL());

						} else {
							bean = bean.substring(0, 1).toUpperCase()
									+ bean.substring(1);
							comboList = this.action.getMyListService()
									.getIdNameList(bean);
						}

						extAddVars += "var " + column.getDataIndex_()
								+ " = new Ext.form.ComboBox({"
								+ "	store: new Ext.data.SimpleStore({"
								+ "        fields: ['id', 'name'],"
								+ "        data : [";

						for (int j = 0; j < comboList.size(); j++) {
							Object[] s = (Object[]) comboList.get(j);
							if (j == 0)
								extAddVars += "        	['" + s[0].toString()
										+ "', '" + s[1].toString() + "']";
							else
								extAddVars += ",        ['" + s[0].toString()
										+ "', '" + s[1].toString() + "']";
						}

						extAddVars += "	]})," + "	valueField: 'id',"
								+ "	displayField:'name',"
								+ "	selectOnFocus:true,"
								+ "	forceSelection:true," + // 不同于search
								"	allowBlank:"
								+ column.isAllowBlank()
								+ ","
								+ "	typeAhead:true,"
								+ "	fieldLabel: '"
								+ column.getName()
								+ (column.isAllowBlank() ? "" : "*")
								+ "',"
								+ "	name:'"
								+ this.getEntityMemberName()
								+ "."
								+ column.getDataIndex()
								+ "',"
								+ "	id:'"
								+ this.getEntityMemberName()
								+ "."
								+ column.getDataIndex()
								+ "',"
								+ "	triggerAction: 'all',"
								+ "	editable: true,"
								+ "	mode:'local',"
								+ "	emptyText:'',"
								+ "	anchor: '90%' ,"
								+ "	blankText:''"
								+ "});\n";
					}
				}
			}
		} // end for (int i = 0; i < listColumnConfig.size(); i++)

		/**
		 * id由uuid产生，不加入到Add的Form中 和 显示的列表中
		 */
		if (extAddItems.startsWith("id,"))
			extAddItems = extAddItems.substring(3);

		if (extColumnModel.endsWith(",\n"))
			extColumnModel = extColumnModel.substring(0, extColumnModel
					.length() - 2);

		// after cycle

		extSearchVars += "var _s_search = new Ext.Button({			"
				+ "type: 'submit', " + "text: '"
				+ action.getText("entity.search") + "',			"
				+ "handler: function() {				"
				+ "store.load({params:{start:0, limit:g_limit" + fieldsFilter
				+ "}});		}		});\n";

		extSearchItems += "{" + "                columnWidth:.1,"
				+ "                layout: 'form',"
				+ "                items: [_s_search]" + "            }\n";
		if (extPreSearchItems.length() > 1) {
			extPreSearchItems = extPreSearchItems.substring(0,
					extPreSearchItems.length() - 2);
		}

		/**
		 * 构建Menu，Function，Script相关元素
		 */
		for (int i = 0; i < listMenuConfig.size(); i++) {

			MenuConfig menu = listMenuConfig.get(i);

			if (menu.getType().equals("function")) {

				extMenus += "'-',{" + "text:'" + menu.getName() + "',"
						+ "iconCls:'selfDef',"
						+ "handler: GridConfigMenuFunction_" + i + "},";

			} else if (menu.getType().equals("functionB")) {
				extMenus += "'-',{" + "text:'" + menu.getName() + "',"
						+ "iconCls:'selfDef',"
						+ "handler: GridConfigMenuFunction_" + i + "},";
			} else if (menu.getType().equals("script")) {

				extMenus += "'-',{" + "text:'" + menu.getName() + "',"
						+ "iconCls:'selfDef',"
						+ "handler: GridConfigMenuScript_" + i + "},";

				extMenuScripts += "function GridConfigMenuScript_" + i + "()"
						+ menu.getValue();
			}
		}

		/**
		 * 构建Render，Function，Script相关元素
		 */
		for (int i = 0; i < listRenderConfig.size(); i++) {

			RenderConfig render = listRenderConfig.get(i);

			if (render.getType().equals("function")) {

				if (render.getColumnDataIndex() == null
						|| render.getColumnDataIndex().equals("")) {
					extRenderFunctions += "function GridConfigRenderFunction_"
							+ i + "(p_value, metadata, record){" + "return \""
							+ render.getRenderer() + "\";}";
				} else {
					extRenderFunctions += "function GridConfigRenderFunction_"
							+ i
							+ "(p_value, metadata, record){"
							+ "return \""
							+ render.getRenderer().replace("\"", "\\\"")
									.replace("${value}", "\"+p_value+\"")
							+ "\";}";
				}

			} else if (render.getType().equals("script")) {

				if (render.getColumnDataIndex() == null
						|| render.getColumnDataIndex().equals("")) {
					extRenderFunctions += "function GridConfigRenderFunction_"
							+ i + "(p_value, metadata, record)"
							+ render.getRenderer();
				} else {
					extRenderFunctions += "function GridConfigRenderFunction_"
							+ i
							+ "(p_value, metadata, record)"
							+ render.getRenderer().replace("\"", "\\\"")
									.replace("${value}", "p_value");
				}

			}

			extRenderColumns += ",{header: '" + render.getName() + "',"
					+ " width: 100, sortable: true,"
					+ " renderer: GridConfigRenderFunction_" + i + ","
					+ " dataIndex: '" + render.getColumnDataIndex() + "'}";
		}

		/**
		 * 准备完毕
		 */
		this.isPrepared = true;
	}

	/**
	 * menu, function配置私有类 配置一个名为name的menu，关联到一个function
	 * 完成功能将名为columnDataIndex的列的值置为value
	 */
	private class MenuConfig {

		private String name;

		private String columnDataIndex;

		private String value;

		private String type;

		private String action;

		private boolean single;

		private boolean ajax;

		private String checkColumn = "id"; // 勾选操作functionB，
											// gridjs.jsp中的GridConfigMenuFunction
											// 所取的列

		public MenuConfig(String name, String columnDataIndex, String value,
				String type) {
			this.name = name;
			this.columnDataIndex = columnDataIndex;
			this.value = value;
			this.type = type;
		}

		public MenuConfig(String name, String action, String checkColumn,
				boolean single, boolean ajax, String type) {
			this.name = name;
			this.action = action;
			this.checkColumn = checkColumn;
			this.single = single;
			this.ajax = ajax;
			this.type = type;
		}

		public String getColumnDataIndex() {
			return columnDataIndex;
		}

		public String getColumnDataIndex_() {
			return columnDataIndex.replace(".", "_");
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public String getType() {
			return type;
		}

		public String getAction() {
			return action;
		}

		public boolean isSingle() {
			return single;
		}

		public boolean isAjax() {
			return ajax;
		}

		public String getCheckColumn() {
			return checkColumn;
		}

		public void setCheckColumn(String checkColumn) {
			this.checkColumn = checkColumn;
		}
	}

	private class RenderConfig {

		private String name;

		private String renderer;

		private String columnDataIndex;

		private String type;

		public RenderConfig(String name, String renderer,
				String columnDataIndex, String type) {
			this.name = name;
			this.renderer = renderer;
			this.columnDataIndex = columnDataIndex;
			this.type = type;
		}

		public String getColumnDataIndex() {
			return columnDataIndex;
		}

		public String getName() {
			return name;
		}

		public String getRenderer() {
			return renderer;
		}

		public String getType() {
			return type;
		}
	}

	public String getExcelFiledsFilter() {
		return excelFiledsFilter;
	}

	public void setExcelFiledsFilter(String excelFiledsFilter) {
		this.excelFiledsFilter = excelFiledsFilter;
	}

	public boolean isCanBatchAdd() {
		return canBatchAdd;
	}

	/**
	 * 设置是否显示复选框一列
	 * 
	 * @param isShow :
	 *            true 显示 ;false 不显示
	 */
	public void setShowCheckBox(boolean showCheckBox) {
		this.showCheckBox = showCheckBox;
	}

	public boolean isShowCheckBox() {
		return showCheckBox;
	}

	public boolean checkBeforeAddMenu(String targetAction) {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us != null) {
			Set capabilitySet = us.getUserPriority().keySet();
			// if(targetAction.indexOf("_")<0){
			// targetAction =
			// targetAction.substring(0,targetAction.lastIndexOf(".")) + "_*" +
			// targetAction.substring(targetAction.lastIndexOf("."),
			// targetAction.length());
			// }
			// String fullCapability = targetAction.substring(0,
			// targetAction.lastIndexOf("_")+1) + "*" +
			// targetAction.substring(targetAction.lastIndexOf("."),
			// targetAction.length());
			// if(capabilitySet.contains(fullCapability) ||
			// capabilitySet.contains(targetAction)){
			// return true;
			// }
			String fullCapability = "";
			if (targetAction.indexOf("_") < 0) {
				targetAction = targetAction.substring(0, targetAction
						.lastIndexOf("."))
						+ "_list" + ".action";
				fullCapability = targetAction.substring(0, targetAction
						.lastIndexOf("_") + 1)
						+ "*" + ".action";

			} else {
				fullCapability = targetAction.substring(0, targetAction
						.lastIndexOf("_") + 1)
						+ "*" + ".action";
			}
			if (capabilitySet.contains(fullCapability)
					|| capabilitySet.contains(targetAction)) {
				return true;
			}
		}else{
		String admin = (String)ServletActionContext.getRequest()
			.getSession().getAttribute("admin");
			if(admin.equals("1")){
				return true;
			}
		}
		return false;
	}

	public boolean isCanExcelUpdate() {
		return canExcelUpdate;
	}

	public void setCanExcelUpdate(boolean canExcelUpdate) {
		this.canExcelUpdate = canExcelUpdate;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	//FIXME: 改为调用updateColumn方法，去掉GridConfig.setPrepared(boolean), isPrepared()方法
	public void setPrepared(boolean isPrepared) {
		this.isPrepared = isPrepared;
	}

	public boolean isCanExcelExport() {
		return canExcelExport;
	}

	public void setCanExcelExport(boolean canExcelExport) {
		this.canExcelExport = canExcelExport;
	}

	public boolean isColumnFit() {
		return columnFit;
	}

	public void setColumnFit(boolean columnFit) {
		this.columnFit = columnFit;
	}
}
