package io.prediction.tools.softwaremanager

import io.prediction.commons._

object Backup {
  val config = new Config()

  val settingsMap = Map(
    "algoInfos" -> config.getSettingsAlgoInfos,
    "algos" -> config.getSettingsAlgos,
    "apps" -> config.getSettingsApps,
    "engineInfos" -> config.getSettingsEngineInfos,
    "engines" -> config.getSettingsEngines,
    "offlineEvalMetricInfos" -> config.getSettingsOfflineEvalMetricInfos,
    "offlineEvalMetrics" -> config.getSettingsOfflineEvalMetrics,
    "offlineEvalResults" -> config.getSettingsOfflineEvalResults,
    "offlineEvals" -> config.getSettingsOfflineEvals,
    "offlineEvalSplitterInfos" -> config.getSettingsOfflineEvalSplitterInfos,
    "offlineEvalSplitters" -> config.getSettingsOfflineEvalSplitters,
    "offlineTunes" -> config.getSettingsOfflineTunes,
    "paramGenInfos" -> config.getSettingsParamGenInfos,
    "paramGens" -> config.getSettingsParamGens,
    "systemInfos" -> config.getSettingsSystemInfos,
    "users" -> config.getSettingsUsers)

  def main(args: Array[String]) {
    println("PredictionIO Backup Utility")
    println()

    val backupDir = try {
      args(0)
    } catch {
      case e: ArrayIndexOutOfBoundsException => {
      	println("Usage: backup <directory_of_backup_files>")
      	sys.exit(1)
      }
    }

    val backupDirFile = new java.io.File(backupDir)
    if (!backupDirFile.exists && !backupDirFile.mkdirs) {
      println(s"Unable to create directory ${backupDir}. Aborting...")
      sys.exit(1)
  	}

  	settingsMap map { s =>
      val fn = s"${backupDir}/${s._1}.bin"
      val fos = new java.io.FileOutputStream(fn)
      try {
        fos.write(s._2.backup())
        println(s"Backed up to ${fn}")
      } finally {
        fos.close()
      }
	}

	println("Backup completed.")
  }
}
