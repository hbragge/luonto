<% define 'State', :for => State do %>
case STATE_<%= name %>:
<%iinc%>
  if (<%= transition.condition %>) {
<%iinc%>
     sm_switch(STATE_<%= transition.targetState.targetIdentifier %>);
<%idec%>
  }
  break;
<%idec%>
<% end %>
