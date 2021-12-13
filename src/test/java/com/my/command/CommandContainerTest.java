package com.my.command;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.my.exception.CommandException;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import com.my.web.command.exhibition.GetExhibitionByTopic;
import com.my.web.command.exhibition.SortExhibitionByStringValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class CommandContainerTest {

  private static HttpServletRequest mockRequest;

  @BeforeAll
  static void setUp() {
    mockRequest = mock(HttpServletRequest.class);
    Map<String, Command> map = new HashMap<>();
    map.put("command", new SortExhibitionByStringValue());
  }

  @Test
  void getCommandShouldReturnNullIfCommandDoesNotExist() throws CommandException {
    when(mockRequest.getParameter(Params.COMMAND)).thenReturn("get_exhibition_by_topic");

    try (MockedStatic<CommandContainer> container = Mockito.mockStatic(CommandContainer.class)) {

      container.when(() -> CommandContainer.getCommand(mockRequest))
          .thenCallRealMethod();

      Assertions.assertNotNull(CommandContainer.getCommand(mockRequest));
      Assertions
          .assertInstanceOf(GetExhibitionByTopic.class, CommandContainer.getCommand(mockRequest));
    }
  }


  @Test
  void getCommandShouldReturnCommandIfAny() {

    when(mockRequest.getParameter(Params.COMMAND)).thenReturn("test");

    try (MockedStatic<CommandContainer> container = Mockito.mockStatic(CommandContainer.class)) {

      container.when(() -> CommandContainer.getCommand(mockRequest))
          .thenCallRealMethod();

      CommandException ex = Assertions
          .assertThrows(CommandException.class, () -> CommandContainer.getCommand(mockRequest));
      Assertions.assertEquals(Logs.NO_SUCH_COMMAND, ex.getMessage());
    }
  }
}