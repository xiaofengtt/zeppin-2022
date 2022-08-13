package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserControlRealmProxy extends cn.zeppin.product.ntb.bean.UserControl
    implements RealmObjectProxy, UserControlRealmProxyInterface {

    static final class UserControlColumnInfo extends ColumnInfo
        implements Cloneable {

        public long userIndex;
        public long showIndex;

        UserControlColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(2);
            this.userIndex = getValidColumnIndex(path, table, "UserControl", "user");
            indicesMap.put("user", this.userIndex);
            this.showIndex = getValidColumnIndex(path, table, "UserControl", "show");
            indicesMap.put("show", this.showIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final UserControlColumnInfo otherInfo = (UserControlColumnInfo) other;
            this.userIndex = otherInfo.userIndex;
            this.showIndex = otherInfo.showIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final UserControlColumnInfo clone() {
            return (UserControlColumnInfo) super.clone();
        }

    }
    private UserControlColumnInfo columnInfo;
    private ProxyState<cn.zeppin.product.ntb.bean.UserControl> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("user");
        fieldNames.add("show");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    UserControlRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (UserControlColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<cn.zeppin.product.ntb.bean.UserControl>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public String realmGet$user() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.userIndex);
    }

    public void realmSet$user(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'user' cannot be changed after object was created.");
    }

    @SuppressWarnings("cast")
    public boolean realmGet$show() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.showIndex);
    }

    public void realmSet$show(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.showIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.showIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("UserControl")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("UserControl");
            realmObjectSchema.add(new Property("user", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("show", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("UserControl");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_UserControl")) {
            Table table = sharedRealm.getTable("class_UserControl");
            table.addColumn(RealmFieldType.STRING, "user", Table.NULLABLE);
            table.addColumn(RealmFieldType.BOOLEAN, "show", Table.NOT_NULLABLE);
            table.addSearchIndex(table.getColumnIndex("user"));
            table.setPrimaryKey("user");
            return table;
        }
        return sharedRealm.getTable("class_UserControl");
    }

    public static UserControlColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_UserControl")) {
            Table table = sharedRealm.getTable("class_UserControl");
            final long columnCount = table.getColumnCount();
            if (columnCount != 2) {
                if (columnCount < 2) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 2 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 2 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 2 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final UserControlColumnInfo columnInfo = new UserControlColumnInfo(sharedRealm.getPath(), table);

            if (!table.hasPrimaryKey()) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'user' in existing Realm file. @PrimaryKey was added.");
            } else {
                if (table.getPrimaryKey() != columnInfo.userIndex) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key annotation definition was changed, from field " + table.getColumnName(table.getPrimaryKey()) + " to field user");
                }
            }

            if (!columnTypes.containsKey("user")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'user' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("user") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'user' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.userIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(),"@PrimaryKey field 'user' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("user"))) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'user' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            }
            if (!columnTypes.containsKey("show")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'show' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("show") != RealmFieldType.BOOLEAN) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'show' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.showIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'show' does support null values in the existing Realm file. Use corresponding boxed type for field 'show' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'UserControl' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_UserControl";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static cn.zeppin.product.ntb.bean.UserControl createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        cn.zeppin.product.ntb.bean.UserControl obj = null;
        if (update) {
            Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserControl.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("user")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("user"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserControl.class), false, Collections.<String> emptyList());
                    obj = new io.realm.UserControlRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("user")) {
                if (json.isNull("user")) {
                    obj = (io.realm.UserControlRealmProxy) realm.createObjectInternal(cn.zeppin.product.ntb.bean.UserControl.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.UserControlRealmProxy) realm.createObjectInternal(cn.zeppin.product.ntb.bean.UserControl.class, json.getString("user"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'user'.");
            }
        }
        if (json.has("show")) {
            if (json.isNull("show")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'show' to null.");
            } else {
                ((UserControlRealmProxyInterface) obj).realmSet$show((boolean) json.getBoolean("show"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static cn.zeppin.product.ntb.bean.UserControl createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        cn.zeppin.product.ntb.bean.UserControl obj = new cn.zeppin.product.ntb.bean.UserControl();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("user")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UserControlRealmProxyInterface) obj).realmSet$user(null);
                } else {
                    ((UserControlRealmProxyInterface) obj).realmSet$user((String) reader.nextString());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("show")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'show' to null.");
                } else {
                    ((UserControlRealmProxyInterface) obj).realmSet$show((boolean) reader.nextBoolean());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'user'.");
        }
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static cn.zeppin.product.ntb.bean.UserControl copyOrUpdate(Realm realm, cn.zeppin.product.ntb.bean.UserControl object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (cn.zeppin.product.ntb.bean.UserControl) cachedRealmObject;
        } else {
            cn.zeppin.product.ntb.bean.UserControl realmObject = null;
            boolean canUpdate = update;
            if (canUpdate) {
                Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserControl.class);
                long pkColumnIndex = table.getPrimaryKey();
                String value = ((UserControlRealmProxyInterface) object).realmGet$user();
                long rowIndex = Table.NO_MATCH;
                if (value == null) {
                    rowIndex = table.findFirstNull(pkColumnIndex);
                } else {
                    rowIndex = table.findFirstString(pkColumnIndex, value);
                }
                if (rowIndex != Table.NO_MATCH) {
                    try {
                        objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserControl.class), false, Collections.<String> emptyList());
                        realmObject = new io.realm.UserControlRealmProxy();
                        cache.put(object, (RealmObjectProxy) realmObject);
                    } finally {
                        objectContext.clear();
                    }
                } else {
                    canUpdate = false;
                }
            }

            if (canUpdate) {
                return update(realm, realmObject, object, cache);
            } else {
                return copy(realm, object, update, cache);
            }
        }
    }

    public static cn.zeppin.product.ntb.bean.UserControl copy(Realm realm, cn.zeppin.product.ntb.bean.UserControl newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (cn.zeppin.product.ntb.bean.UserControl) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            cn.zeppin.product.ntb.bean.UserControl realmObject = realm.createObjectInternal(cn.zeppin.product.ntb.bean.UserControl.class, ((UserControlRealmProxyInterface) newObject).realmGet$user(), false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((UserControlRealmProxyInterface) realmObject).realmSet$show(((UserControlRealmProxyInterface) newObject).realmGet$show());
            return realmObject;
        }
    }

    public static long insert(Realm realm, cn.zeppin.product.ntb.bean.UserControl object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserControl.class);
        long tableNativePtr = table.getNativeTablePointer();
        UserControlColumnInfo columnInfo = (UserControlColumnInfo) realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserControl.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((UserControlRealmProxyInterface) object).realmGet$user();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.showIndex, rowIndex, ((UserControlRealmProxyInterface)object).realmGet$show(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserControl.class);
        long tableNativePtr = table.getNativeTablePointer();
        UserControlColumnInfo columnInfo = (UserControlColumnInfo) realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserControl.class);
        long pkColumnIndex = table.getPrimaryKey();
        cn.zeppin.product.ntb.bean.UserControl object = null;
        while (objects.hasNext()) {
            object = (cn.zeppin.product.ntb.bean.UserControl) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((UserControlRealmProxyInterface) object).realmGet$user();
                long rowIndex = Table.NO_MATCH;
                if (primaryKeyValue == null) {
                    rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
                } else {
                    rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
                }
                if (rowIndex == Table.NO_MATCH) {
                    rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
                } else {
                    Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
                }
                cache.put(object, rowIndex);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.showIndex, rowIndex, ((UserControlRealmProxyInterface)object).realmGet$show(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, cn.zeppin.product.ntb.bean.UserControl object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserControl.class);
        long tableNativePtr = table.getNativeTablePointer();
        UserControlColumnInfo columnInfo = (UserControlColumnInfo) realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserControl.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((UserControlRealmProxyInterface) object).realmGet$user();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
        }
        cache.put(object, rowIndex);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.showIndex, rowIndex, ((UserControlRealmProxyInterface)object).realmGet$show(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserControl.class);
        long tableNativePtr = table.getNativeTablePointer();
        UserControlColumnInfo columnInfo = (UserControlColumnInfo) realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserControl.class);
        long pkColumnIndex = table.getPrimaryKey();
        cn.zeppin.product.ntb.bean.UserControl object = null;
        while (objects.hasNext()) {
            object = (cn.zeppin.product.ntb.bean.UserControl) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((UserControlRealmProxyInterface) object).realmGet$user();
                long rowIndex = Table.NO_MATCH;
                if (primaryKeyValue == null) {
                    rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
                } else {
                    rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
                }
                if (rowIndex == Table.NO_MATCH) {
                    rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
                }
                cache.put(object, rowIndex);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.showIndex, rowIndex, ((UserControlRealmProxyInterface)object).realmGet$show(), false);
            }
        }
    }

    public static cn.zeppin.product.ntb.bean.UserControl createDetachedCopy(cn.zeppin.product.ntb.bean.UserControl realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        cn.zeppin.product.ntb.bean.UserControl unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (cn.zeppin.product.ntb.bean.UserControl)cachedObject.object;
            } else {
                unmanagedObject = (cn.zeppin.product.ntb.bean.UserControl)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new cn.zeppin.product.ntb.bean.UserControl();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((UserControlRealmProxyInterface) unmanagedObject).realmSet$user(((UserControlRealmProxyInterface) realmObject).realmGet$user());
        ((UserControlRealmProxyInterface) unmanagedObject).realmSet$show(((UserControlRealmProxyInterface) realmObject).realmGet$show());
        return unmanagedObject;
    }

    static cn.zeppin.product.ntb.bean.UserControl update(Realm realm, cn.zeppin.product.ntb.bean.UserControl realmObject, cn.zeppin.product.ntb.bean.UserControl newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ((UserControlRealmProxyInterface) realmObject).realmSet$show(((UserControlRealmProxyInterface) newObject).realmGet$show());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("UserControl = [");
        stringBuilder.append("{user:");
        stringBuilder.append(realmGet$user() != null ? realmGet$user() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{show:");
        stringBuilder.append(realmGet$show());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserControlRealmProxy aUserControl = (UserControlRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aUserControl.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aUserControl.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aUserControl.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
