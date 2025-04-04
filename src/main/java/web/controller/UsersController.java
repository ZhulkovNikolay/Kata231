package web.controller;


//1. Написать CRUD-приложение. Использовать Spring MVC + Hibernate.

//3. В приложении должна быть страница,
// на которую выводятся все юзеры с возможностью добавлять, удалять и изменять юзера.

//5. Внесите изменения в конфигурацию для работы с базой данных.
// Вместо SessionFactory должен использоваться EntityManager.

//6. Используйте только GET/POST маппинги

//7. Используйте ReqestParam аннотацию, использование аннотации PathVariable запрещено

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDao;
import web.dao.UserDaoImpl;
import web.models.User;

@Controller
@RequestMapping("users")
public class UsersController {

    private final UserDaoImpl userDaoImpl;

    @Autowired
    public UsersController(UserDaoImpl userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }

    @GetMapping()
    public String index(Model model) {
        //получим всех людей из ДАО и отправим на вьюшку
        model.addAttribute("users", userDaoImpl.index());
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //Получим 1 человека по ИД из ДАО и передадим на вьюшку
        model.addAttribute("user", userDaoImpl.show(id));
        return "show";
    }

    @GetMapping("/new")
    public String newUser(Model model) { //сюда можно @ModelAttribute("user) User user. Он создаст и положит в модель пустого юзера
        //Если нам нужна таймлифовская форма какого-то объекта,
        //то этот объект мы должны передать на вью через модель
        model.addAttribute("user", new User());
        return "new";
    }

    //здесь мы должны получить данные из формы по адресу /users, создать нового человека
    //положить этого человека в БД
    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userDaoImpl.save(user);
        return "redirect:/users";
    }

    //чтобы редактировать человека, нам надо подцепить его из модели
    //таким образом поля для редактирвоания не будут пустыми
    //они будут заранее заполненными этим человеком
    //Этот метод контроллера СОЗДАЕТ ФОРМУ редактирования
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDaoImpl.show(id));
        return "edit";
    }

    //Этот метод контроллера ПРИНИМАЕТ ДАННЫЕ из формы редактирования
    //принмиает объект Юзер из формы и сразу кладет в модель
    //так же нам нужно принять значение ИД из адреса
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userDaoImpl.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userDaoImpl.delete(id);
        return "redirect:/users";
    }

}
