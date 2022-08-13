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

public class UserGestureRealmProxy extends cn.zeppin.product.ntb.bean.UserGesture
    implements RealmObjectProxy, UserGestureRealmProxyInterface {

    static final class UserGestureColumnInfo extends ColumnInfo
        implements Cloneable {

        public long phoneIndex;
        public long gesturePwdIndex;
        public long errorTimeIndex;
        public long isOpenIndex;

        UserGestureColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(4);
            this.phoneIndex = getValidColumnIndex(path, table, "UserGesture", "phone");
            indicesMap.put("phone", this.phoneIndex);
            this.gesturePwdIndex = getValidColumnIndex(path, table, "UserGesture", "gesturePwd");
            indicesMap.put("gesturePwd", this.gesturePwdIndex);
            this.errorTimeIndex = getValidColumnIndex(path, table, "UserGesture", "errorTime");
            indicesMap.put("errorTime", this.errorTimeIndex);
            this.isOpenIndex = getValidColumnIndex(path, table, "UserGesture", "isOpen");
            indicesMap.put("isOpen", this.isOpenIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final UserGestureColumnInfo otherInfo = (UserGestureColumnInfo) other;
            this.phoneIndex = otherInfo.phoneIndex;
            this.gesturePwdIndex = otherInfo.gesturePwdIndex;
            this.errorTimeIndex = otherInfo.errorTimeIndex;
            this.isOpenIndex = otherInfo.isOpenIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final UserGestureColumnInfo clone() {
            return (UserGestureColumnInfo) super.clone();
        }

    }
    private UserGestureColumnInfo columnInfo;
    private ProxyState<cn.zeppin.product.ntb.bean.UserGesture> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("phone");
        fieldNames.add("gesturePwd");
        fieldNames.add("errorTime");
        fieldNames.add("isOpen");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    UserGestureRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (UserGestureColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<cn.zeppin.product.ntb.bean.UserGesture>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public String realmGet$phone() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.phoneIndex);
    }

    public void realmSet$phone(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'phone' cannot be changed after object was created.");
    }

    @SuppressWarnings("cast")
    public byte[] realmGet$gesturePwd() {
        proxyState.getRealm$realm().checkIfValid();
        return (byte[]) proxyState.getRow$realm().getBinaryByteArray(columnInfo.gesturePwdIndex);
    }

    public void realmSet$gesturePwd(byte[] value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.gesturePwdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBinaryByteArray(columnInfo.gesturePwdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.gesturePwdIndex);
            return;
        }
        proxyState.getRow$realm().setBinaryByteArray(columnInfo.gesturePwdIndex, value);
    }

    @SuppressWarnings("cast")
    public int realmGet$errorTime() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.errorTimeIndex);
    }

    public void realmSet$errorTime(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.errorTimeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.errorTimeIndex, value);
    }

    @SuppressWarnings("cast")
    public boolean realmGet$isOpen() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isOpenIndex);
    }

    public void realmSet$isOpen(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.isOpenIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.isOpenIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("UserGesture")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("UserGesture");
            realmObjectSchema.add(new Property("phone", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("gesturePwd", RealmFieldType.BINARY, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("errorTime", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            realmObjectSchema.add(new Property("isOpen", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("UserGesture");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_UserGesture")) {
            Table table = sharedRealm.getTable("class_UserGesture");
            table.addColumn(RealmFieldType.STRING, "phone", Table.NULLABLE);
            table.addColumn(RealmFieldType.BINARY, "gesturePwd", Table.NULLABLE);
            table.addColumn(RealmFieldType.INTEGER, "errorTime", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.BOOLEAN, "isOpen", Table.NOT_NULLABLE);
            table.addSearchIndex(table.getColumnIndex("phone"));
            table.setPrimaryKey("phone");
            return table;
        }
        return sharedRealm.getTable("class_UserGesture");
    }

    public static UserGestureColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_UserGesture")) {
            Table table = sharedRealm.getTable("class_UserGesture");
            final long columnCount = table.getColumnCount();
            if (columnCount != 4) {
                if (columnCount < 4) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 4 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 4 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 4 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final UserGestureColumnInfo columnInfo = new UserGestureColumnInfo(sharedRealm.getPath(), table);

            if (!table.hasPrimaryKey()) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'phone' in existing Realm file. @PrimaryKey was added.");
            } else {
                if (table.getPrimaryKey() != columnInfo.phoneIndex) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key annotation definition was changed, from field " + table.getColumnName(table.getPrimaryKey()) + " to field phone");
                }
            }

            if (!columnTypes.containsKey("phone")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'phone' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("phone") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'phone' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.phoneIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(),"@PrimaryKey field 'phone' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("phone"))) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'phone' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            }
            if (!columnTypes.containsKey("gesturePwd")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'gesturePwd' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("gesturePwd") != RealmFieldType.BINARY) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'byte[]' for field 'gesturePwd' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.gesturePwdIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'gesturePwd' is required. Either set @Required to field 'gesturePwd' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("errorTime")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'errorTime' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("errorTime") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'errorTime' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.errorTimeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'errorTime' does support null values in the existing Realm file. Use corresponding boxed type for field 'errorTime' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("isOpen")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'isOpen' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("isOpen") != RealmFieldType.BOOLEAN) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'isOpen' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.isOpenIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'isOpen' does support null values in the existing Realm file. Use corresponding boxed type for field 'isOpen' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'UserGesture' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_UserGesture";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static cn.zeppin.product.ntb.bean.UserGesture createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        cn.zeppin.product.ntb.bean.UserGesture obj = null;
        if (update) {
            Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserGesture.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("phone")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("phone"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserGesture.class), false, Collections.<String> emptyList());
                    obj = new io.realm.UserGestureRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("phone")) {
                if (json.isNull("phone")) {
                    obj = (io.realm.UserGestureRealmProxy) realm.createObjectInternal(cn.zeppin.product.ntb.bean.UserGesture.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.UserGestureRealmProxy) realm.createObjectInternal(cn.zeppin.product.ntb.bean.UserGesture.class, json.getString("phone"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'phone'.");
            }
        }
        if (json.has("gesturePwd")) {
            if (json.isNull("gesturePwd")) {
                ((UserGestureRealmProxyInterface) obj).realmSet$gesturePwd(null);
            } else {
                ((UserGestureRealmProxyInterface) obj).realmSet$gesturePwd(JsonUtils.stringToBytes(json.getString("gesturePwd")));
            }
        }
        if (json.has("errorTime")) {
            if (json.isNull("errorTime")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'errorTime' to null.");
            } else {
                ((UserGestureRealmProxyInterface) obj).realmSet$errorTime((int) json.getInt("errorTime"));
            }
        }
        if (json.has("isOpen")) {
            if (json.isNull("isOpen")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isOpen' to null.");
            } else {
                ((UserGestureRealmProxyInterface) obj).realmSet$isOpen((boolean) json.getBoolean("isOpen"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static cn.zeppin.product.ntb.bean.UserGesture createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        cn.zeppin.product.ntb.bean.UserGesture obj = new cn.zeppin.product.ntb.bean.UserGesture();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("phone")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UserGestureRealmProxyInterface) obj).realmSet$phone(null);
                } else {
                    ((UserGestureRealmProxyInterface) obj).realmSet$phone((String) reader.nextString());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("gesturePwd")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UserGestureRealmProxyInterface) obj).realmSet$gesturePwd(null);
                } else {
                    ((UserGestureRealmProxyInterface) obj).realmSet$gesturePwd(JsonUtils.stringToBytes(reader.nextString()));
                }
            } else if (name.equals("errorTime")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'errorTime' to null.");
                } else {
                    ((UserGestureRealmProxyInterface) obj).realmSet$errorTime((int) reader.nextInt());
                }
            } else if (name.equals("isOpen")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isOpen' to null.");
                } else {
                    ((UserGestureRealmProxyInterface) obj).realmSet$isOpen((boolean) reader.nextBoolean());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'phone'.");
        }
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static cn.zeppin.product.ntb.bean.UserGesture copyOrUpdate(Realm realm, cn.zeppin.product.ntb.bean.UserGesture object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (cn.zeppin.product.ntb.bean.UserGesture) cachedRealmObject;
        } else {
            cn.zeppin.product.ntb.bean.UserGesture realmObject = null;
            boolean canUpdate = update;
            if (canUpdate) {
                Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserGesture.class);
                long pkColumnIndex = table.getPrimaryKey();
                String value = ((UserGestureRealmProxyInterface) object).realmGet$phone();
                long rowIndex = Table.NO_MATCH;
                if (value == null) {
                    rowIndex = table.findFirstNull(pkColumnIndex);
                } else {
                    rowIndex = table.findFirstString(pkColumnIndex, value);
                }
                if (rowIndex != Table.NO_MATCH) {
                    try {
                        objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserGesture.class), false, Collections.<String> emptyList());
                        realmObject = new io.realm.UserGestureRealmProxy();
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

    public static cn.zeppin.product.ntb.bean.UserGesture copy(Realm realm, cn.zeppin.product.ntb.bean.UserGesture newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (cn.zeppin.product.ntb.bean.UserGesture) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            cn.zeppin.product.ntb.bean.UserGesture realmObject = realm.createObjectInternal(cn.zeppin.product.ntb.bean.UserGesture.class, ((UserGestureRealmProxyInterface) newObject).realmGet$phone(), false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((UserGestureRealmProxyInterface) realmObject).realmSet$gesturePwd(((UserGestureRealmProxyInterface) newObject).realmGet$gesturePwd());
            ((UserGestureRealmProxyInterface) realmObject).realmSet$errorTime(((UserGestureRealmProxyInterface) newObject).realmGet$errorTime());
            ((UserGestureRealmProxyInterface) realmObject).realmSet$isOpen(((UserGestureRealmProxyInterface) newObject).realmGet$isOpen());
            return realmObject;
        }
    }

    public static long insert(Realm realm, cn.zeppin.product.ntb.bean.UserGesture object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserGesture.class);
        long tableNativePtr = table.getNativeTablePointer();
        UserGestureColumnInfo columnInfo = (UserGestureColumnInfo) realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserGesture.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((UserGestureRealmProxyInterface) object).realmGet$phone();
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
        byte[] realmGet$gesturePwd = ((UserGestureRealmProxyInterface)object).realmGet$gesturePwd();
        if (realmGet$gesturePwd != null) {
            Table.nativeSetByteArray(tableNativePtr, columnInfo.gesturePwdIndex, rowIndex, realmGet$gesturePwd, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.errorTimeIndex, rowIndex, ((UserGestureRealmProxyInterface)object).realmGet$errorTime(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isOpenIndex, rowIndex, ((UserGestureRealmProxyInterface)object).realmGet$isOpen(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserGesture.class);
        long tableNativePtr = table.getNativeTablePointer();
        UserGestureColumnInfo columnInfo = (UserGestureColumnInfo) realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserGesture.class);
        long pkColumnIndex = table.getPrimaryKey();
        cn.zeppin.product.ntb.bean.UserGesture object = null;
        while (objects.hasNext()) {
            object = (cn.zeppin.product.ntb.bean.UserGesture) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((UserGestureRealmProxyInterface) object).realmGet$phone();
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
                byte[] realmGet$gesturePwd = ((UserGestureRealmProxyInterface)object).realmGet$gesturePwd();
                if (realmGet$gesturePwd != null) {
                    Table.nativeSetByteArray(tableNativePtr, columnInfo.gesturePwdIndex, rowIndex, realmGet$gesturePwd, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.errorTimeIndex, rowIndex, ((UserGestureRealmProxyInterface)object).realmGet$errorTime(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isOpenIndex, rowIndex, ((UserGestureRealmProxyInterface)object).realmGet$isOpen(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, cn.zeppin.product.ntb.bean.UserGesture object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserGesture.class);
        long tableNativePtr = table.getNativeTablePointer();
        UserGestureColumnInfo columnInfo = (UserGestureColumnInfo) realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserGesture.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((UserGestureRealmProxyInterface) object).realmGet$phone();
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
        byte[] realmGet$gesturePwd = ((UserGestureRealmProxyInterface)object).realmGet$gesturePwd();
        if (realmGet$gesturePwd != null) {
            Table.nativeSetByteArray(tableNativePtr, columnInfo.gesturePwdIndex, rowIndex, realmGet$gesturePwd, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.gesturePwdIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.errorTimeIndex, rowIndex, ((UserGestureRealmProxyInterface)object).realmGet$errorTime(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isOpenIndex, rowIndex, ((UserGestureRealmProxyInterface)object).realmGet$isOpen(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(cn.zeppin.product.ntb.bean.UserGesture.class);
        long tableNativePtr = table.getNativeTablePointer();
        UserGestureColumnInfo columnInfo = (UserGestureColumnInfo) realm.schema.getColumnInfo(cn.zeppin.product.ntb.bean.UserGesture.class);
        long pkColumnIndex = table.getPrimaryKey();
        cn.zeppin.product.ntb.bean.UserGesture object = null;
        while (objects.hasNext()) {
            object = (cn.zeppin.product.ntb.bean.UserGesture) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((UserGestureRealmProxyInterface) object).realmGet$phone();
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
                byte[] realmGet$gesturePwd = ((UserGestureRealmProxyInterface)object).realmGet$gesturePwd();
                if (realmGet$gesturePwd != null) {
                    Table.nativeSetByteArray(tableNativePtr, columnInfo.gesturePwdIndex, rowIndex, realmGet$gesturePwd, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.gesturePwdIndex, rowIndex, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.errorTimeIndex, rowIndex, ((UserGestureRealmProxyInterface)object).realmGet$errorTime(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isOpenIndex, rowIndex, ((UserGestureRealmProxyInterface)object).realmGet$isOpen(), false);
            }
        }
    }

    public static cn.zeppin.product.ntb.bean.UserGesture createDetachedCopy(cn.zeppin.product.ntb.bean.UserGesture realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        cn.zeppin.product.ntb.bean.UserGesture unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (cn.zeppin.product.ntb.bean.UserGesture)cachedObject.object;
            } else {
                unmanagedObject = (cn.zeppin.product.ntb.bean.UserGesture)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new cn.zeppin.product.ntb.bean.UserGesture();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((UserGestureRealmProxyInterface) unmanagedObject).realmSet$phone(((UserGestureRealmProxyInterface) realmObject).realmGet$phone());
        ((UserGestureRealmProxyInterface) unmanagedObject).realmSet$gesturePwd(((UserGestureRealmProxyInterface) realmObject).realmGet$gesturePwd());
        ((UserGestureRealmProxyInterface) unmanagedObject).realmSet$errorTime(((UserGestureRealmProxyInterface) realmObject).realmGet$errorTime());
        ((UserGestureRealmProxyInterface) unmanagedObject).realmSet$isOpen(((UserGestureRealmProxyInterface) realmObject).realmGet$isOpen());
        return unmanagedObject;
    }

    static cn.zeppin.product.ntb.bean.UserGesture update(Realm realm, cn.zeppin.product.ntb.bean.UserGesture realmObject, cn.zeppin.product.ntb.bean.UserGesture newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ((UserGestureRealmProxyInterface) realmObject).realmSet$gesturePwd(((UserGestureRealmProxyInterface) newObject).realmGet$gesturePwd());
        ((UserGestureRealmProxyInterface) realmObject).realmSet$errorTime(((UserGestureRealmProxyInterface) newObject).realmGet$errorTime());
        ((UserGestureRealmProxyInterface) realmObject).realmSet$isOpen(((UserGestureRealmProxyInterface) newObject).realmGet$isOpen());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("UserGesture = [");
        stringBuilder.append("{phone:");
        stringBuilder.append(realmGet$phone() != null ? realmGet$phone() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{gesturePwd:");
        stringBuilder.append(realmGet$gesturePwd() != null ? realmGet$gesturePwd() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{errorTime:");
        stringBuilder.append(realmGet$errorTime());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isOpen:");
        stringBuilder.append(realmGet$isOpen());
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
        UserGestureRealmProxy aUserGesture = (UserGestureRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aUserGesture.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aUserGesture.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aUserGesture.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
