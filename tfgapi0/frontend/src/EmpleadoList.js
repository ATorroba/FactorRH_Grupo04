import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class EmpleadoList extends Component {

    constructor(props) {
        super(props);
        this.state = {empleados: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/empleados')
            .then(response => response.json())
            .then(data => this.setState({empleados: data}));
    }

    async remove(email) {
        await fetch(`/empleados/${email}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedEmpleados = [...this.state.empleados].filter(i => i.email !== email);
            this.setState({empleados: updatedEmpleados});
        });
    }

    render() {
        const {empleados} = this.state;

        const empleadoList = empleados.map(empleado => {
            return <tr key={empleado.email}>
                <td style={{whiteSpace: 'nowrap'}}>{empleado.email}</td>
                <td>{empleado.password}</td>
             
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/empleados/" + empleado.email}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(empleado.email)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/empleados/new">Add Empleado</Button>
                    </div>
                    <h3>Empleados</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="15%">Email</th>
                            <th width="15%">Password</th>
                            <th width="15%">Actions</th>
                       
                        </tr>
                        </thead>
                        <tbody>
                        {empleadoList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default EmpleadoList;