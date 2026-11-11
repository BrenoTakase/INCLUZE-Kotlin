package com.aether.incluze.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0019\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0019\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000b"}, d2 = {"Lcom/aether/incluze/data/local/ReportDao;", "", "getAllReports", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/aether/incluze/data/local/ReportEntity;", "insertReport", "", "report", "(Lcom/aether/incluze/data/local/ReportEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateReport", "app_debug"})
@androidx.room.Dao
public abstract interface ReportDao {
    
    @androidx.room.Query(value = "SELECT * FROM reports ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.aether.incluze.data.local.ReportEntity>> getAllReports();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertReport(@org.jetbrains.annotations.NotNull
    com.aether.incluze.data.local.ReportEntity report, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateReport(@org.jetbrains.annotations.NotNull
    com.aether.incluze.data.local.ReportEntity report, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}