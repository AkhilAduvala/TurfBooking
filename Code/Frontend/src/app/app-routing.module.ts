import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', loadChildren: () => import('./features/auth/login/login.module').then(m => m.LoginModule) },
  { path: 'register', loadChildren: () => import('./features/auth/register/register.module').then(m => m.RegisterModule) },
  { path: 'profile', loadChildren: () => import('./features/user-profile/user-profile.module').then(m => m.UserProfileModule) },
  { path: 'booking', loadChildren: () => import('./features/booking/booking.module').then(m => m.BookingModule) },
  { path: 'payment', loadChildren: () => import('./features/payment/payment.module').then(m => m.PaymentModule) },
  { path: 'notifications', loadChildren: () => import('./features/notification/notification.module').then(m => m.NotificationModule) },
  { path: 'admin-dashboard', loadChildren: () => import('./features/admin-dashboard/admin-dashboard.module').then(m => m.AdminDashboardModule) },
  { path: 'manager-dashboard', loadChildren: () => import('./features/manager-dashboard/manager-dashboard.module').then(m => m.ManagerDashboardModule) },
  { path: '**', redirectTo: '/login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
