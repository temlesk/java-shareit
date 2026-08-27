package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(ItemDto itemDto, Long ownerId);

    ItemDto update(Long itemId, Long ownerId, ItemDto itemDto);

    ItemDto getById(Long itemId);

    List<ItemDto> getByOwner(Long ownerId);

    List<ItemDto> search(String text);
}
