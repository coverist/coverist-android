// Generated by Dagger (https://dagger.dev).
package dev.yjyoon.coverist.ui.cover.generation.done;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.internal.lifecycle.HiltViewModelMap.KeySet")
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ShowCoverViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
  @Override
  public String get() {
    return provide();
  }

  public static ShowCoverViewModel_HiltModules_KeyModule_ProvideFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provide() {
    return Preconditions.checkNotNullFromProvides(ShowCoverViewModel_HiltModules.KeyModule.provide());
  }

  private static final class InstanceHolder {
    private static final ShowCoverViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new ShowCoverViewModel_HiltModules_KeyModule_ProvideFactory();
  }
}