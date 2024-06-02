package com.dimrnhhh.moneytopia.pages

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.components.about.Contributor
import com.dimrnhhh.moneytopia.components.about.SocialMedia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutPage(
    navController: NavController
) {
    val context = LocalContext.current
    val openGitHub = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/dimrnhhh"))
    val openBehance= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.behance.net/dimrnhhh"))
    val openLinkedIn= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/dimrnhhh/"))
    val openInstagram= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/dimrnhhh/"))
    val packageManager = context.packageManager
    val packageName = context.packageName
    val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
    } else {
        packageManager.getPackageInfo(packageName, 0)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.about_button),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                AppIcon()
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "v"+packageInfo.versionName,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Normal
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SocialMedia(
                        icon = ImageVector.vectorResource(R.drawable.ic_github),
                        title = "GitHub",
                        onClick = { context.startActivity(openGitHub) }
                    )
                    SocialMedia(
                        icon = ImageVector.vectorResource(R.drawable.ic_behance),
                        title = "Behance",
                        onClick = { context.startActivity(openBehance) }
                    )
                    SocialMedia(
                        icon = ImageVector.vectorResource(R.drawable.ic_linkedin),
                        title = "LinkedIn",
                        onClick = { context.startActivity(openLinkedIn) }
                    )
                    SocialMedia(
                        icon = ImageVector.vectorResource(R.drawable.ic_instagram),
                        title = "Instagram",
                        onClick = { context.startActivity(openInstagram) }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.contrib_label),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = MaterialTheme.shapes.large)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                            )
                    ) {
                        val items = listOf(
                            Contributor(
                                name = stringResource(R.string.contrib_name),
                                avatar = ImageBitmap.imageResource(R.drawable.ic_avatar),
                                role = stringResource(R.string.dev_role),
                            ),
                            Contributor(
                                name = stringResource(R.string.contrib_name),
                                avatar = ImageBitmap.imageResource(R.drawable.ic_avatar),
                                role = stringResource(R.string.uiux_role),
                            ),
                            Contributor(
                                name = stringResource(R.string.contrib_name),
                                avatar = ImageBitmap.imageResource(R.drawable.ic_avatar),
                                role = stringResource(R.string.support_role),
                            ),
                            Contributor(
                                name = stringResource(R.string.contrib_name),
                                avatar = ImageBitmap.imageResource(R.drawable.ic_avatar),
                                role = stringResource(R.string.translation_role),
                            )
                        )
                        items.forEachIndexed { index, it ->
                            ListItem(
                                modifier = Modifier
                                    .clickable {  },
                                leadingContent = {
                                    Image(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape),
                                        bitmap = it.avatar,
                                        contentDescription = null
                                    )
                                },
                                headlineContent = { Text(text = it.name, style = MaterialTheme.typography.titleMedium) },
                                supportingContent = { Text(text = it.role) },
                                colors = ListItemDefaults.colors(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                            )
                            if(index < items.lastIndex) {
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppIcon() {
    Icon(
        modifier = Modifier
            .size(80.dp)
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.shapes.large
            )
            .padding(15.dp),
        imageVector = ImageVector.vectorResource(R.drawable.app_logo),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
    )
}
