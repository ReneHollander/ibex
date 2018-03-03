import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';

import {AccountService} from '../services/account.service';
import {ToastComponent} from '../shared/toast/toast.component';
import {City} from "../shared/models/city.model";
import {CityService} from "../services/city.service";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {

    cities: City[];

    registerForm: FormGroup;
    // name = new FormControl('', [
    //     Validators.required,
    //     Validators.minLength(2),
    //     Validators.maxLength(30),
    //     Validators.pattern('[a-zA-Z0-9_-\\s]*')
    // ]);
    // email = new FormControl('', [
    //     Validators.required,
    //     Validators.minLength(3),
    //     Validators.maxLength(100)
    // ]);
    // password = new FormControl('', [
    //     Validators.required,
    //     Validators.minLength(6)
    // ]);
    // role = new FormControl('', [
    //     Validators.required
    // ]);
    isLoading: boolean = true;

    constructor(private formBuilder: FormBuilder,
                private router: Router,
                public toast: ToastComponent,
                private userService: AccountService,
                private cityService: CityService) {
    }

    async getCities(): Promise<void> {
        this.isLoading = true;
        this.cities = await this.cityService.getEnabledCities();
        this.isLoading = false;
    }

    async ngOnInit() {
        await this.getCities();
        this.registerForm = this.formBuilder.group({
            // name: this.name,
            // email: this.email,
            // password: this.password,
            // role: this.role
        });
    }

    // setClassName() {
    //     return {'has-danger': !this.name.pristine && !this.name.valid};
    // }
    //
    // setClassEmail() {
    //     return {'has-danger': !this.email.pristine && !this.email.valid};
    // }
    //
    // setClassPassword() {
    //     return {'has-danger': !this.password.pristine && !this.password.valid};
    // }
    //
    // register() {
    //     // this.userService.register(this.registerForm.value).subscribe(
    //     //     res => {
    //     //         this.toast.setMessage('you successfully registered!', 'success');
    //     //         this.router.navigate(['/login']);
    //     //     },
    //     //     error => this.toast.setMessage('email already exists', 'danger')
    //     // );
    // }
}
