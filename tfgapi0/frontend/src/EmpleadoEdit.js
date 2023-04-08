import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class EmpleadoEdit extends Component {

    emptyItem = {
        idEmpleado: '',
        nombre: '',
        apellido_1: '',
        apellido_2: '',
        NIF: '',
        NASS: '',
        direccion: '',
        CP: '',
        email_particular: '',
        email: '',
        telefono: '',
        fecha_alta: '',
        fecha_baja: '',
        IBAN: '',
        SWIFT: '',
        sueldo_base: '',
        antiguedad: '',
        password: ''

    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.idEmpleado !== 'new') { // 
            const empleado = await (await fetch(`/empleados/${this.props.match.params.idEmpleado}`)).json();
            this.setState({ item: empleado });
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = { ...this.state.item };
        item[name] = value;
        this.setState({ item });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const { item } = this.state;

        await fetch('/empleados' + ((this.props.match.params.idEmpleado !== 'new') ? '/' + item.idEmpleado : ''), {
            method: (this.props.match.params.idEmpleado !== 'new') ? 'PUT' : 'POST', //(item.email) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/empleados');
    }

    render() {
        const { item } = this.state;
        const title = <h2>{item.idEmpleado ? 'Edit Empleado' : 'Add Empleado'}</h2>;

        return <div>
            <AppNavbar />
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>

                    <FormGroup>
                        <Label for="idEmpleado">id</Label>
                        <Input type="text" name="idEmpleado" id="idEmpleado" value={item.idEmpleado || ''}
                            onChange={this.handleChange} autoComplete="idEmpleado" />
                    </FormGroup>

                    <FormGroup>
                        <Label for="password">password</Label>
                        <Input type="text" name="password" id="password" value={item.password || ''}
                            onChange={this.handleChange} autoComplete="password" />
                    </FormGroup>

                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/empleados">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(EmpleadoEdit);