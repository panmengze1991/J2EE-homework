package sc.ustc.dao;


import sc.ustc.model.BaseBean;

public abstract class BaseDAO<T extends BaseBean> {
    public void openDBConnection() {
        Conversation.openDBConnection();
    }

    public void closeDBConnection() {
        Conversation.closeDBConnection();
    }

    public T query(T t){
        return Conversation.getObject(t);
    }

    public boolean delete(T t){
        return Conversation.deleteObject(t);
    }

    public boolean insert(T t){
        return Conversation.insertObject(t);
    }

    public boolean update(T t){
        return Conversation.updateObject(t);
    }}
