import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthService} from './auth.service';

@Injectable()
export class AuthGuardAdmin implements CanActivate {

    constructor(private authService: AuthService, private router: Router) {
    }

    async canActivate() {
        await this.authService.initial();
        if (this.authService.isAdmin) {
            return true;
        } else {
            this.router.navigate(['/login']);
            return false;
        }
    }

}
