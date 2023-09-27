package ru.vk.itmo.test.cheshevandrey;

import ru.vk.itmo.Dao;
import ru.vk.itmo.Entry;
import ru.vk.itmo.cheshevandrey.InMemoryDao;
import ru.vk.itmo.test.DaoFactory;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;

@DaoFactory
public class InMemoryFactory implements DaoFactory.Factory<MemorySegment, Entry<MemorySegment>> {

    @Override
    public Dao<MemorySegment, Entry<MemorySegment>> createDao() {
        return new InMemoryDao();
    }

    @Override
    public String toString(MemorySegment memorySegment) {

        int memorySegmentSize = (int) memorySegment.byteSize();

        byte[] byteArray = new byte[(int) memorySegmentSize];

        int offset = 0;
        while (offset < memorySegmentSize) {
            byteArray[offset] = memorySegment.get(ValueLayout.JAVA_BYTE, offset);
            offset++;
        }

        return new String(byteArray);
    }

    @Override
    public MemorySegment fromString(String data) {
        return (data == null)
                ? MemorySegment.NULL :
                MemorySegment.ofArray(data.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Entry<MemorySegment> fromBaseEntry(Entry<MemorySegment> baseEntry) {
        return baseEntry;
    }
}
