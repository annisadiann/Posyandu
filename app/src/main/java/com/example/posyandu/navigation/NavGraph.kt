package com.example.posyandu.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.posyandu.data.local.database.PosyanduDatabase
import com.example.posyandu.data.repository.AdminRepository
import com.example.posyandu.data.repository.IbuBalitaRepository
import com.example.posyandu.ui.auth.LoginScreen
import com.example.posyandu.ui.auth.RegisterScreen
import com.example.posyandu.ui.dashboard.DashboardScreen
import com.example.posyandu.ui.ibubalita.IbuBalitaScreen
import com.example.posyandu.ui.ibubalita.AddEditIbuScreen
import com.example.posyandu.ui.jadwal.JadwalKontrolScreen
import com.example.posyandu.ui.jadwal.AddEditJadwalScreen
import com.example.posyandu.ui.klinis.LayananKlinisScreen
import com.example.posyandu.ui.laporan.LaporanScreen
import com.example.posyandu.ui.laporan.DetailLaporanScreen
import com.example.posyandu.ui.pemeriksaan.PemeriksaanScreen
import com.example.posyandu.ui.pemeriksaan.InputDetailPemeriksaanScreen
import com.example.posyandu.ui.splash.SplashScreen
import com.example.posyandu.viewmodel.AdminViewModel
import com.example.posyandu.viewmodel.AdminViewModelFactory
import com.example.posyandu.viewmodel.IbuBalitaViewModel
import com.example.posyandu.viewmodel.IbuBalitaViewModelFactory

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    val context = LocalContext.current
    val db = PosyanduDatabase.getDatabase(context)

    val adminRepository = AdminRepository(db.adminDao())
    val adminViewModel: AdminViewModel = viewModel(factory = AdminViewModelFactory(adminRepository))

    val ibuRepository = IbuBalitaRepository(
        db.ibuBalitaDao(),
        db.jadwalKontrolDao(),
        db.pemeriksaanDao(),
        db.laporanDao()
    )
    val ibuViewModel: IbuBalitaViewModel = viewModel(factory = IbuBalitaViewModelFactory(ibuRepository))

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // --- AUTH SECTION ---
        composable(route = Screen.Splash.route) {
            SplashScreen(viewModel = adminViewModel, onAnimationFinished = { isLoggedIn ->
                val dest = if (isLoggedIn) Screen.Dashboard.route else Screen.Login.route
                navController.navigate(dest) { popUpTo(Screen.Splash.route) { inclusive = true } }
            })
        }
        composable(route = Screen.Login.route) {
            LoginScreen(viewModel = adminViewModel, onLoginSuccess = {
                navController.navigate(Screen.Dashboard.route) { popUpTo(Screen.Login.route) { inclusive = true } }
            }, onNavigateToRegister = { navController.navigate(Screen.Register.route) })
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(viewModel = adminViewModel, onRegisterSuccess = {
                navController.navigate(Screen.Login.route) { popUpTo(Screen.Register.route) { inclusive = true } }
            })
        }

        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                viewModel = ibuViewModel, // Menghubungkan data ke Dashboard
                onMenuSelected = { route ->
                    if (route == "logout") {
                        navController.navigate(Screen.Login.route) { popUpTo(Screen.Dashboard.route) { inclusive = true } }
                    } else {
                        navController.navigate(route)
                    }
                }
            )
        }

        // --- DATA IBU & BALITA ---
        composable(route = Screen.IbuBalita.route) {
            IbuBalitaScreen(
                viewModel = ibuViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddIbu = { navController.navigate(Screen.AddEditIbu.route) },
                onNavigateToEditIbu = { id -> navController.navigate(Screen.AddEditIbu.createRoute(id)) }
            )
        }
        composable(
            route = Screen.AddEditIbu.route + "?ibuId={ibuId}",
            arguments = listOf(navArgument("ibuId") { type = NavType.IntType; defaultValue = -1 })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("ibuId") ?: -1
            AddEditIbuScreen(ibuId = id, viewModel = ibuViewModel, onNavigateBack = { navController.popBackStack() })
        }

        // --- LAYANAN KLINIS ---
        composable(route = Screen.LayananKlinis.route) {
            LayananKlinisScreen(
                onNavigateToPemeriksaan = { navController.navigate(Screen.Pemeriksaan.route) },
                onNavigateToJadwal = { navController.navigate(Screen.JadwalKontrol.route) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- PEMERIKSAAN ---
        composable(route = Screen.Pemeriksaan.route) {
            PemeriksaanScreen(
                viewModel = ibuViewModel,
                onNavigateBack = { navController.popBackStack() },
                onPatientSelected = { id -> navController.navigate("input_detail_pemeriksaan/$id") }
            )
        }

        composable(
            route = "input_detail_pemeriksaan/{pasienId}",
            arguments = listOf(navArgument("pasienId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("pasienId") ?: 0
            InputDetailPemeriksaanScreen(
                pasienId = id,
                ibuViewModel = ibuViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToJadwal = { navController.navigate(Screen.JadwalKontrol.route) }
            )
        }

        // --- JADWAL KONTROL ---
        composable(route = Screen.JadwalKontrol.route) {
            JadwalKontrolScreen(
                viewModel = ibuViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddJadwal = { navController.navigate("add_edit_jadwal/-1") },
                onNavigateToEditJadwal = { id -> navController.navigate("add_edit_jadwal/$id") }
            )
        }

        composable(
            route = "add_edit_jadwal/{jadwalId}",
            arguments = listOf(navArgument("jadwalId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("jadwalId") ?: -1
            AddEditJadwalScreen(
                jadwalId = id,
                viewModel = ibuViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- LAPORAN ---
        composable(route = Screen.Laporan.route) {
            LaporanScreen(
                viewModel = ibuViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { id ->
                    navController.navigate(Screen.DetailLaporan.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.DetailLaporan.route,
            arguments = listOf(navArgument("laporanId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("laporanId") ?: 0
            DetailLaporanScreen(
                viewModel = ibuViewModel,
                laporanId = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}