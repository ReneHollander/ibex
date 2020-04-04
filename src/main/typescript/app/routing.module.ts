import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './main/home/home.component';
import {RegisterComponent} from './main/register/register.component';
import {LoginComponent} from './main/login/login.component';
import {LogoutComponent} from './main/logout/logout.component';
import {AccountComponent} from './main/account/account.component';
import {NotFoundComponent} from './main/not-found/not-found.component';
import {AuthGuardAdmin} from './service/guard/auth-guard-admin.service';
import {AuthGuardLoggedIn} from './service/guard/auth-guard-loggedin.service';
import {ImpressComponent} from './main/impress/impress.component';
import {AuthGuardUser} from './service/guard/auth-guard-login.service';
import {BuildInformationComponent} from './main/buildinfo/build-information.component';

const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'user', loadChildren: () => import('app/user/user.module').then(m => m.UserModule), canActivate: [AuthGuardUser]},
    {path: 'admin', loadChildren: () => import('app/admin/admin.module').then(m => m.AdminModule), canActivate: [AuthGuardAdmin]},
    {path: 'login', component: LoginComponent},
    {path: 'logout', component: LogoutComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'account', component: AccountComponent, canActivate: [AuthGuardLoggedIn]},
    {path: 'impress', component: ImpressComponent},
    {path: 'buildinformation', component: BuildInformationComponent},
    {path: 'notfound', component: NotFoundComponent},
    {path: '**', redirectTo: '/notfound'},
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
    exports: [RouterModule]
})
export class RoutingModule {
}
