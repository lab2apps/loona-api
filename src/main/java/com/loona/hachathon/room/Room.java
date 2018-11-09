package com.loona.hachathon.room;

import com.loona.hachathon.space.Space;
import com.loona.hachathon.util.CsvAttributeConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "image_urls")
    @Convert(converter = CsvAttributeConverter.class)
    private List<String> imageUrls;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "rent_type")
    private String rentType;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name="space_uuid")
    private Space sticker;

}
