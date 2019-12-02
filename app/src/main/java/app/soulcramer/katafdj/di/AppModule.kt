package app.soulcramer.katafdj.di

import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module(override = true) {


    //    factory<UserRepository> { BrowseTeamsPresenter(get(), get()) }
//
//    viewModel { UserViewModel(get()) }
//
//    viewModel { SessionViewModel(get(), get(), get()) }
//
//    viewModel { UserListViewModel(get()) }
}
