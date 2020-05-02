package cz.student_evidence.data.memory;

public class DataItem<ENTITY, KEY> {
    private KEY key;
    private ENTITY entity;
    private DataItem<ENTITY, KEY> next;
    private DataItem<ENTITY, KEY> before;

    public DataItem(KEY key, ENTITY entity) {
        this.key = key;
        this.entity = entity;
    }

    public KEY getKey() {
        return key;
    }

    public void setKey(KEY key) {
        this.key = key;
    }

    public ENTITY getEntity() {
        return entity;
    }

    public void setEntity(ENTITY entity) {
        this.entity = entity;
    }

    public boolean hasNext() {
        return next != null;
    }

    public DataItem<ENTITY, KEY> getNext() {
        return next;
    }

    public void setNext(DataItem<ENTITY, KEY> next) {
        this.next = next;
    }

    public DataItem<ENTITY, KEY> getBefore() {
        return before;
    }

    public void setBefore(DataItem<ENTITY, KEY> before) {
        this.before = before;
    }
}
