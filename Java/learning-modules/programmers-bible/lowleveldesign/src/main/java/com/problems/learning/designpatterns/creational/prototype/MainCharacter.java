package com.problems.learning.designpatterns.creational.prototype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class MainCharacter extends Prototype<MainCharacter> {

    private String name;
    private String age;
    private String gender;
    private String money;

    @Override
    public MainCharacter proto() {
        return new MainCharacter(this.name, this.age, this.gender, this.money);
    }
}
