actions = require './actions.coffee'
http = require '../commons/request.coffee'
createStore = require '../commons/createStore.coffee'

module.exports =
  createdDashboard: createStore actions, 'createdDashboard'

  views: createStore actions, 'views',
    request: (autocb) ->
      await http.get '/views', defer response
      response

  dashboards: createStore actions, 'dashboards',
    request: (autocb)->
      await http.get '/dashboards', defer response
      response

  dashboardDetail: createStore actions, 'dashboardDetail',
    request: (options,autocb)->
      await http.get "/dashboards/#{options.id}", defer response
      response