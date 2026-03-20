package com.problems.learning.designpatterns.creational.prototype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class GameCharacter extends Prototype<GameCharacter> {

    private String name;
    private String age;
    private String gender;

    @Override
    public GameCharacter proto() {
        return new GameCharacter(this.name, this.age, this.gender);
    }
}
