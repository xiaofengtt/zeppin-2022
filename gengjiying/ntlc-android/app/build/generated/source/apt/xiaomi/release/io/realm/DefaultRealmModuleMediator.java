package io.realm;


import android.util.JsonReader;
import io.realm.RealmObjectSchema;
import io.realm.internal.ColumnInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmModel>> modelClasses = new HashSet<Class<? extends RealmModel>>();
        modelClasses.add(cn.zeppin.product.ntb.bean.UserGesture.class);
        modelClasses.add(cn.zeppin.product.ntb.bean.UserControl.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public Table createTable(Class<? extends RealmModel> clazz, SharedRealm sharedRealm) {
        checkClass(clazz);

        if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
            return io.realm.UserGestureRealmProxy.initTable(sharedRealm);
        } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
            return io.realm.UserControlRealmProxy.initTable(sharedRealm);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public RealmObjectSchema createRealmObjectSchema(Class<? extends RealmModel> clazz, RealmSchema realmSchema) {
        checkClass(clazz);

        if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
            return io.realm.UserGestureRealmProxy.createRealmObjectSchema(realmSchema);
        } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
            return io.realm.UserControlRealmProxy.createRealmObjectSchema(realmSchema);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public ColumnInfo validateTable(Class<? extends RealmModel> clazz, SharedRealm sharedRealm, boolean allowExtraColumns) {
        checkClass(clazz);

        if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
            return io.realm.UserGestureRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
            return io.realm.UserControlRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public List<String> getFieldNames(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
            return io.realm.UserGestureRealmProxy.getFieldNames();
        } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
            return io.realm.UserControlRealmProxy.getFieldNames();
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public String getTableName(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
            return io.realm.UserGestureRealmProxy.getTableName();
        } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
            return io.realm.UserControlRealmProxy.getTableName();
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> clazz, Object baseRealm, Row row, ColumnInfo columnInfo, boolean acceptDefaultValue, List<String> excludeFields) {
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        try {
            objectContext.set((BaseRealm) baseRealm, row, columnInfo, acceptDefaultValue, excludeFields);
            checkClass(clazz);

            if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
                return clazz.cast(new io.realm.UserGestureRealmProxy());
            } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
                return clazz.cast(new io.realm.UserControlRealmProxy());
            } else {
                throw getMissingProxyClassException(clazz);
            }
        } finally {
            objectContext.clear();
        }
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmModel, RealmObjectProxy> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
            return clazz.cast(io.realm.UserGestureRealmProxy.copyOrUpdate(realm, (cn.zeppin.product.ntb.bean.UserGesture) obj, update, cache));
        } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
            return clazz.cast(io.realm.UserControlRealmProxy.copyOrUpdate(realm, (cn.zeppin.product.ntb.bean.UserControl) obj, update, cache));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, RealmModel object, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

        if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
            io.realm.UserGestureRealmProxy.insert(realm, (cn.zeppin.product.ntb.bean.UserGesture) object, cache);
        } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
            io.realm.UserControlRealmProxy.insert(realm, (cn.zeppin.product.ntb.bean.UserControl) object, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
                io.realm.UserGestureRealmProxy.insert(realm, (cn.zeppin.product.ntb.bean.UserGesture) object, cache);
            } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
                io.realm.UserControlRealmProxy.insert(realm, (cn.zeppin.product.ntb.bean.UserControl) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
                    io.realm.UserGestureRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
                    io.realm.UserControlRealmProxy.insert(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, RealmModel obj, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
            io.realm.UserGestureRealmProxy.insertOrUpdate(realm, (cn.zeppin.product.ntb.bean.UserGesture) obj, cache);
        } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
            io.realm.UserControlRealmProxy.insertOrUpdate(realm, (cn.zeppin.product.ntb.bean.UserControl) obj, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
                io.realm.UserGestureRealmProxy.insertOrUpdate(realm, (cn.zeppin.product.ntb.bean.UserGesture) object, cache);
            } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
                io.realm.UserControlRealmProxy.insertOrUpdate(realm, (cn.zeppin.product.ntb.bean.UserControl) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
                    io.realm.UserGestureRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
                    io.realm.UserControlRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
            return clazz.cast(io.realm.UserGestureRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
            return clazz.cast(io.realm.UserControlRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmModel> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
            return clazz.cast(io.realm.UserGestureRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
            return clazz.cast(io.realm.UserControlRealmProxy.createUsingJsonStream(realm, reader));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E realmObject, int maxDepth, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) realmObject.getClass().getSuperclass();

        if (clazz.equals(cn.zeppin.product.ntb.bean.UserGesture.class)) {
            return clazz.cast(io.realm.UserGestureRealmProxy.createDetachedCopy((cn.zeppin.product.ntb.bean.UserGesture) realmObject, 0, maxDepth, cache));
        } else if (clazz.equals(cn.zeppin.product.ntb.bean.UserControl.class)) {
            return clazz.cast(io.realm.UserControlRealmProxy.createDetachedCopy((cn.zeppin.product.ntb.bean.UserControl) realmObject, 0, maxDepth, cache));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

}
