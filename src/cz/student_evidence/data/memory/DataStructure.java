package cz.student_evidence.data.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DataStructure<ENTITY, KEY> {

    private DataItem<ENTITY, KEY> firstItem;

    public boolean addItem(KEY key, ENTITY entity) {
        DataItem<ENTITY, KEY> dataItem = new DataItem<>(key, entity);

        if (firstItem == null) {
            firstItem = dataItem;
            return true;
        }

        DataItem<ENTITY, KEY> current = firstItem;
        DataItem<ENTITY, KEY> next = firstItem.getNext();

        boolean isAdded = false;

        do {
            if (next == null) {
                dataItem.setBefore(current);
                current.setNext(dataItem);
                isAdded = true;
            } else {
                current = next;
                next = current.getNext();
            }
        } while (!isAdded);

        return true;
    }

    public List<ENTITY> getAll() {
        if (firstItem == null) return Collections.emptyList();

        List<ENTITY> list = new ArrayList<>();

        DataItem<ENTITY, KEY> current = firstItem;

        do {
            ENTITY entity = current.getEntity();
            if (entity != null) list.add(entity);
            current = current.getNext();
        } while (current != null);

        return list;
    }

    public List<ENTITY> getAllWithId(KEY id) {
        if (firstItem == null || id == null) return Collections.emptyList();

        List<ENTITY> list = new ArrayList<>();

        DataItem<ENTITY, KEY> current = firstItem;

        do {
            KEY key = current.getKey();
            ENTITY entity = current.getEntity();
            if (id.equals(key) && entity != null) list.add(entity);
            current = current.getNext();
        } while (current != null);

        return list;
    }

    public Optional<ENTITY> getFirst(KEY id) {
        if (firstItem == null || id == null) return Optional.empty();

        DataItem<ENTITY, KEY> current = firstItem;

        do {
            KEY key = current.getKey();
            ENTITY entity = current.getEntity();
            if (id.equals(key)) return Optional.ofNullable(entity);
            current = current.getNext();
        } while (current != null);

        return Optional.empty();

    }

    public boolean deleteAllWithId(KEY id) {
        if (firstItem == null || id == null) return false;

        boolean isDeleted = false;

        DataItem<ENTITY, KEY> current = firstItem;

        do {
            KEY key = current.getKey();
            if (id.equals(key)) {
                DataItem<ENTITY, KEY> before = current.getBefore();
                DataItem<ENTITY, KEY> next = current.getNext();

                if (before == null) {
                    firstItem = next;
                } else {
                    before.setNext(next);
                }

                if (next != null) next.setBefore(before);

                isDeleted = true;
            }
            current = current.getNext();
        } while (current != null);

        return isDeleted;
    }
}
