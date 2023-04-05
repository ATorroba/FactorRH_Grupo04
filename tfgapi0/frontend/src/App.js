import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import EmpleadoList from './EmpleadoList';
import EmpleadoEdit from "./EmpleadoEdit";

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/empleados' exact={true} component={EmpleadoList}/>
            <Route path='/empleados/:email' component={EmpleadoEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;