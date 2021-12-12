package com.my.command;

import com.my.exception.CommandException;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import com.my.web.command.exhibiitonHall.AppointExhibitionForHall;
import com.my.web.command.admin.ShowAppointExhibitionForHall;
import com.my.web.command.exhibition.GetCurrentExhibitionsListCommand;
import com.my.web.command.admin.FilterBookingsCommand;
import com.my.web.command.admin.FilterUsersCommand;
import com.my.web.command.admin.FindUserByLoginCommand;
import com.my.web.command.admin.SetRoleForUserCommand;
import com.my.web.command.admin.ShowAdminBookingsCommand;
import com.my.web.command.admin.ShowAdminCreatePageCommand;
import com.my.web.command.admin.ShowAdminEditPageCommand;
import com.my.web.command.admin.ShowAdminPageCommand;
import com.my.web.command.admin.ShowAdminUsersCommand;
import com.my.web.command.admin.SortUsersCommand;
import com.my.web.command.booking.BuyCommand;
import com.my.web.command.booking.CancelBookingCommand;
import com.my.web.command.booking.GetBookingListCommand;
import com.my.web.command.booking.ShowBookingInfoCommand;
import com.my.web.command.booking.SortBookingsCommand;
import com.my.web.command.exhibiitonHall.DeleteHallFromExhibitionCommand;
import com.my.web.command.exhibiitonHall.SetHallsForExhibitionCommand;
import com.my.web.command.exhibiitonHall.ShowHallsForExhibitions;
import com.my.web.command.exhibition.AdminCreateExhibitionCommand;
import com.my.web.command.exhibition.AdminDeleteExhibitionCommand;
import com.my.web.command.exhibition.AdminUpdateExhibitionCommand;
import com.my.web.command.exhibition.ExhibitionDeleteConfirmationCommand;
import com.my.web.command.exhibition.FilterExhibitionsByDateCommand;
import com.my.web.command.exhibition.GetExhibitionByTopic;
import com.my.web.command.exhibition.GetExhibitionCommand;
import com.my.web.command.exhibition.SortExhibitionByStringValue;
import com.my.web.command.hall.CreateHallCommand;
import com.my.web.command.hall.FilterHallsCommand;
import com.my.web.command.exhibition.ExhibitionSuccessCommand;
import com.my.web.command.hall.GetHallByNumberCommand;
import com.my.web.command.hall.ShowAdminCreateHallPage;
import com.my.web.command.hall.ShowAdminHallsCommand;
import com.my.web.command.hall.SortHallsCommand;
import com.my.web.command.user.ChangeLoginCommand;
import com.my.web.command.user.ChangePasswordCommand;
import com.my.web.command.user.LogOutCommand;
import com.my.web.command.user.LoginCommand;
import com.my.web.command.exhibition.GetAdminExhibitionsListCommand;
import com.my.web.command.user.RegistrationCommand;
import com.my.web.command.user.ReturnExhibitionsPage;
import com.my.web.command.user.ShowLoginPageCommand;
import com.my.web.command.user.ShowUserAccountCommand;
import com.my.web.command.user.ShowUserBookingsCommand;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class CommandContainer {

  private static final Logger LOG = Logger.getLogger(CommandContainer.class);

  private static Map<String, Command> commands;

  private CommandContainer() {
  }

  static {
    commands = new HashMap<>();

    commands.put("registration", new RegistrationCommand());
    commands.put("login", new LoginCommand());
    commands.put("show_login_page", new ShowLoginPageCommand());
    commands.put("logout", new LogOutCommand());
    commands.put("get_current_exhibitions_list", new GetCurrentExhibitionsListCommand());
    commands.put("return_exhibitions_page", new ReturnExhibitionsPage());

    //Admin commands
    commands.put("show_admin_page", new ShowAdminPageCommand());
    commands.put("show_admin_create_page", new ShowAdminCreatePageCommand());
    commands.put("show_admin_edit_page", new ShowAdminEditPageCommand());
    commands.put("get_exhibitions_list", new GetAdminExhibitionsListCommand());
    commands.put("admin_exhibition_update", new AdminUpdateExhibitionCommand());
    commands.put("admin_exhibition_create", new AdminCreateExhibitionCommand());
    commands.put("exhibition_success", new ExhibitionSuccessCommand());
    commands.put("exhibition_delete_confirmation", new ExhibitionDeleteConfirmationCommand());
    commands.put("delete_exhibition", new AdminDeleteExhibitionCommand());
    commands.put("show_halls_for_exhibition", new ShowHallsForExhibitions());
    commands.put("set_halls_for_exhibition", new SetHallsForExhibitionCommand());
    commands.put("delete_hall_from_exhibition", new DeleteHallFromExhibitionCommand());
    commands.put("get_exhibition", new GetExhibitionCommand());
    commands.put("show_appoint_exhibition_for_hall", new ShowAppointExhibitionForHall());
    commands.put("appoint_exhibition_for_hall", new AppointExhibitionForHall());

//    commands.put("exhibition_status", new GetExhibitionStatusListCommand());
//    commands.put("get_currency_list", new GetCurrencyListCommand());
//    commands.put("get_hall_status_list", new GetHallStatusListCommand());
//    commands.put("get_hall_list", new GetHallListCommand());

//    commands.put("sort_exhibitions", new SortExhibitionsCommand());
//    bookings
//    commands.put("get_booking_status_list", new GetBookingStatusListCommand());

    commands.put("get_booking_list", new GetBookingListCommand());
    commands.put("show_admin_bookings", new ShowAdminBookingsCommand());
    commands.put("filter_bookings", new FilterBookingsCommand());
    commands.put("sort_bookings", new SortBookingsCommand());

//    user
    commands.put("show_admin_users", new ShowAdminUsersCommand());
    commands.put("filter_users", new FilterUsersCommand());
    commands.put("update_user", new SetRoleForUserCommand());
    commands.put("sort_users", new SortUsersCommand());
    commands.put("find_user_by_login", new FindUserByLoginCommand());

//    halls
    commands.put("show_admin_halls", new ShowAdminHallsCommand());
    commands.put("filter_halls", new FilterHallsCommand());
    commands.put("sort_halls", new SortHallsCommand());
    commands.put("show_admin_create_hall_page", new ShowAdminCreateHallPage());
    commands.put("admin_hall_create", new CreateHallCommand());
    commands.put("get_hall_by_number", new GetHallByNumberCommand());

    //User commands
    commands.put("get_exhibition_by_topic", new GetExhibitionByTopic());
    commands.put("show_booking_info", new ShowBookingInfoCommand());
    commands.put("buy_command", new BuyCommand());
    commands.put("show_user_account", new ShowUserAccountCommand());
    commands.put("show_user_bookings", new ShowUserBookingsCommand());
    commands.put("cancel_booking_command", new CancelBookingCommand());
    commands.put("change_login_command", new ChangeLoginCommand());
    commands.put("change_pass_command", new ChangePasswordCommand());
    commands.put("filter_exhibitions_by_date", new FilterExhibitionsByDateCommand());
    commands.put("sort_exhibition_by_string_value", new SortExhibitionByStringValue());
  }

  public static Command getCommand(HttpServletRequest req) throws CommandException {
    String command = req.getParameter(Params.COMMAND);
    if (!commands.containsKey(command)) {
      String errorMes = Logs.NO_SUCH_COMMAND;
      LOG.warn(errorMes);
      throw new CommandException(errorMes);
    }
    return commands.get(command);
  }

}
